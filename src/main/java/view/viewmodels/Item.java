package view.viewmodels;

import java.util.Collection;

public class Item {

    private int id;
    private int version;
    private String name;
    private int price;
    private int quantity;
    private Collection<Category> categories;

    public Item(int id, int version, String name, int price, int quantity, Collection<Category> categories) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Collection<Category> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", categories=" + categories +
                '}';
    }
}
