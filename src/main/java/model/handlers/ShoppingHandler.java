package model.handlers;

import model.handlers.exceptions.DatabaseException;
import model.repository.dao.ItemsDao;
import model.repository.dao.OrdersDao;
import model.repository.entities.ItemEntity;
import model.repository.entities.OrderEntity;
import model.repository.entities.OrderItemEntity;
import org.hibernate.dialect.Database;
import utils.Converter;
import view.viewmodels.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

// TODO: Should add a Cart class and a periodic refresh for removing user carts that have not been used for some time.
public class ShoppingHandler {

    public static final int ORDER_OK          = 1;
    public static final int ORDER_FAIL        = 2;
    public static final int EXCEPTION         = 3;

    private static final String[] accessRoles = {"Admin", "Worker"};
    private static final AccessControl accessControl = new AccessControl(accessRoles);

    private HashMap<Integer, Cart> carts = new HashMap<>();

    public static class Cart {
        public HashMap<Integer, Cart.Item> items = new HashMap<>();

        public Item getItem(int itemId) {
            return items.get(itemId);
        }

        public int getTotalAmount() {
            int total = 0;
            for (Cart.Item item : items.values()) {
                total += item.amount;
            }
            return total;
        }

        public int getCost() {
            int cost = 0;
            for (Cart.Item item : items.values()) {
                cost += item.item.price * item.amount;
            }
            return cost;
        }

        public void addItem(int itemId, int amount) throws Exception {
            ItemEntity toAdd = ItemsDao.findById(itemId);
            if (toAdd == null) {
                items.remove(itemId);
                return;
            }
            Cart.Item cartItem = items.get(itemId);
            if (cartItem == null) {
                cartItem = new Cart.Item(toAdd, amount);
                items.put(itemId, cartItem);
            } else {
                cartItem.amount += amount;
            }
            if (cartItem.amount < 0) {
                cartItem.amount = 0;
            }
        }

        public void setItem(int itemId, int value) throws Exception {
            ItemEntity toAdd = ItemsDao.findById(itemId);
            if (toAdd == null) {
                items.remove(itemId);
                return;
            }
            Cart.Item cartItem = items.get(itemId);
            if (cartItem == null) {
                cartItem = new Cart.Item(toAdd, value);
                items.put(itemId, cartItem);
            } else {
                cartItem.amount = value;
            }
            if (cartItem.amount < 0) {
                cartItem.amount = 0;
            }
        }

        @Override
        public String toString() {
            return "Cart{" +
                    "items=" + items +
                    '}';
        }

        public static class Item {
            public ItemEntity item;
            public int amount;

            public Item(ItemEntity item, int amount) {
                this.item = item;
                this.amount = amount;
            }

            public int getCost() {
                return item.price*amount;
            }

            @Override
            public String toString() {
                return "Item{" +
                        "item=" + item.name + "(" + item.id + ")" +
                        ", amount=" + amount +
                        ", cost=" + getCost() +
                        '}';
            }
        }
    }

    private ShoppingHandler() { }

    public static final ShoppingHandler instance = new ShoppingHandler();

    public static ShoppingHandler getInstance() {
        return instance;
    }

    private Cart getUserCart(int userId) {
        Cart cart = carts.get(userId);
        if (cart == null) {
            cart = new Cart();
            carts.put(userId, cart);
        }
        return cart;
    }

    public synchronized void addItemToUserCart(int userId, int itemId, int amount) throws Exception {
        Cart cart = getUserCart(userId);
        cart.addItem(itemId, amount);
    }

    public synchronized void setItemInUserCart(int userId, int itemId, int value) throws Exception {
        Cart cart = getUserCart(userId);
        cart.setItem(itemId, value);
    }

    public synchronized void removeFromUserCart(int userId, int itemId) {
        Cart cart = getUserCart(userId);
        cart.items.remove(itemId);
    }

    public synchronized void clearUserCart(int userId) {
        Cart cart = getUserCart(userId);
        cart.items.clear();
    }

    public synchronized void removeUserCart(int userId) {
        carts.remove(userId);
    }

    public synchronized Cart getUserShoppingCart(int userId) {
        return getUserCart(userId);
    }

    public synchronized int placeOrder(int userId) {
        Cart cart = getUserCart(userId);
        int response = 0;
        if (cart.getTotalAmount() > 0) {
            // Convert to order
            OrderEntity order = new OrderEntity();
            order.send();
            for (Cart.Item cartItem : cart.items.values()) {
                if (cartItem.amount > 0) {
                    // associations created internally to order and item inside the constructor
                    new OrderItemEntity(cartItem.item, order, cartItem.amount, cartItem.item.price);
                }
            }
            try {
                OrdersDao.insert(userId, order);
                cart.items.clear();
                response = ORDER_OK;
            } catch (DatabaseException e) {
                e.printStackTrace();
                response = EXCEPTION;
            }
        } else {
            response = ORDER_FAIL;
        }
        return response;
    }

    public List<Order> getAllOrders(List<String> access) throws IllegalAccessException, DatabaseException {
        if (accessControl.validateAccess(null, access))
            return Converter.toOrders(OrdersDao.findAll());
        throw new IllegalAccessException();
    }

    public void deliverOrder(int orderId) throws Exception {
        OrdersDao.deliverOrder(orderId);
    }

}
