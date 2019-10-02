package view.viewmodels;

import java.util.List;

public class Cart {

    public final List<CartItem> items;
    public final int cost;

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                ", cost=" + cost +
                '}';
    }

    public Cart(List<CartItem> items) {
        this.items = items;
        int total = 0;
        for (CartItem item : items) {
            total +=  item.cost;
        }
        this.cost = total;
    }

    public static class CartItem {

        public final Item item;
        public final String name;
        public final int stock;
        public final int amount;
        public final int cost;
        public final int price;

        public CartItem(Item item, int amount) {
            this.item = item;
            this.name = item.getName();
            this.price = item.getPrice();
            this.stock = item.getQuantity();
            this.amount = amount;
            this.cost = item.getPrice()*amount;
        }

        @Override
        public String toString() {
            return "CartItem{" +
                    "item=" + item +
                    ", name='" + name + '\'' +
                    ", stock=" + stock +
                    ", amount=" + amount +
                    ", cost=" + cost +
                    ", price=" + price +
                    '}';
        }
    }
}
