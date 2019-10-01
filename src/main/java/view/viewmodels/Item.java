package view.viewmodels;

import java.util.Collection;

public class Item {

    private final int id;
    private final int version;
    private final String name;
    private final int price;
    private final int quantity;
    private final Collection<Category> categories;

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

    public boolean hasCategory(Category category) {
        for (Category c : categories) {
            if (c.getName().equals(category.getName()))
                return true;
        }
        return false;
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
