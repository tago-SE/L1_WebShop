package utils;

import model.handlers.ShoppingHandler;
import model.repository.entities.*;
import view.viewmodels.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Converts Entity objects into Model objects and vice versa.
 */
public class Converter {

    // ~~~~~~~~~~
    //  Cart
    // ~~~~~~~~~~

    public static Cart toCart(ShoppingHandler.Cart entity) {
        List<Cart.CartItem> cartItems = new ArrayList<>();
        for (ShoppingHandler.Cart.Item item : entity.items.values()) {
            cartItems.add(new Cart.CartItem(toItem(item.item), item.amount));
        }
        return new Cart(cartItems);
    }

    // ~~~~~~~~~~
    //  Order
    // ~~~~~~~~~~

    public static OrderItem toOrderItem(OrderItemEntity entity) {
        return new OrderItem(entity.id, entity.version, toItem(entity.getItem()), entity.amount, entity.price_per_item);
    }

    public static Order toOrder(OrderEntity entity) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemEntity oi : entity.orderItems) {
            orderItems.add(toOrderItem(oi));
        }
        return new Order(entity.id, entity.version, toUser(entity.user), entity.status.toString(), entity.sent, entity.delivered, orderItems);
    }

    public static List<Order> toOrders(List<OrderEntity> entities) {
        List<Order> orderList = new ArrayList<>();
        for (OrderEntity oe : entities) {
            orderList.add(toOrder(oe));
        }
        return orderList;
    }

    // ~~~~~~~~~~
    //  Category
    // ~~~~~~~~~~

    public static Category toCategory(CategoryEntity entity) {
       return new Category(entity.id, entity.name, entity.version);
    }

    public static List<Category> toCategories(List<CategoryEntity> entities) {
        List<Category> resultList = new ArrayList<>();
        for (CategoryEntity e : entities) {
            resultList.add(toCategory(e));
        }
        return resultList;
    }

    public static CategoryEntity toCategoryEntity(Category model) {
        CategoryEntity e = new CategoryEntity();
        e.name = model.getName();
        e.id = model.getId();
        e.version = model.getVersion();
        return e;
    }

    public static List<CategoryEntity> toCategoryEntities(List<Category> models) {
        List<CategoryEntity> resultList = new ArrayList<>();
        for (Category m : models) {
            resultList.add(toCategoryEntity(m));
        }
        return resultList;
    }

    // ~~~~~~~
    //  Item
    // ~~~~~~~

    public static ItemEntity toItemEntity(Item model) {
        ItemEntity e = new ItemEntity();
        e.id = model.getId();
        e.version = model.getVersion();
        e.name = model.getName();
        e.price = model.getPrice();
        e.quantity = model.getQuantity();
        e.categories = new HashSet<>(toCategoryEntities((List<Category>) model.getCategories()));
        return e;
    }

    public static Item toItem(ItemEntity entity) {
        return new Item(entity.id, entity.version, entity.name, entity.price, entity.quantity, toCategories(new ArrayList<>(entity.categories)));
    }

    public static List<Item> toItems(List<ItemEntity> entities) {
        List<Item> resultList = new ArrayList<>();
        for (ItemEntity e : entities) {
            resultList.add(toItem(e));
        }
        return resultList;
    }

    // ~~~~~~~
    //  User
    // ~~~~~~~

    public static UserEntity toUserEntity(User model) {
        UserEntity entity = new UserEntity();
        entity.id = model.id;
        entity.version = model.version;
        entity.name = model.name;
        entity.firstName = model.firstName;
        entity.lastName = model.lastName;
        System.out.println("MODEL__" + model.accessRoles);
        entity.accessRoles = new HashSet<>(model.accessRoles);
        return entity;
    }

    public static User toUser(UserEntity entity) {
        return new User(entity.id, entity.version, entity.name, new ArrayList<>(entity.accessRoles), entity.firstName, entity.lastName);
    }

    public static List<User> toUsers(List<UserEntity> entities) {
        List<User> resultList = new ArrayList<>();
        for (UserEntity e : entities) {
            resultList.add(toUser(e));
        }
        return resultList;
    }

}
