package model.repository.entities;

import view.viewmodels.Category;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Items")
@NamedQueries({
        @NamedQuery(name = "Item.findAll", query = "SELECT i FROM  ItemEntity i"),
        @NamedQuery(name = "Item.findByName", query = "SELECT i FROM  ItemEntity i WHERE i.name = :name"),
        @NamedQuery(name = "Item.deleteById", query = "DELETE FROM ItemEntity i WHERE i.id =:id"),
})
public class ItemEntity implements EntityInt {

    public static String TABLE = "Items";

    @Id
    @GeneratedValue(generator = "incrementor")
    @Column(name = "item_id", unique = true)
    public int id;

    @Version
    public int version;

    @Column(name = "name", unique = true, nullable = false)
    public String name;

    @Column(name = "price", nullable = false)
    public int price;

    @Column(name = "quantity", nullable = false)
    public int quantity;

    @ManyToMany(mappedBy="items", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<CategoryEntity> categories = new HashSet<>();

    public ItemEntity() { }

    public ItemEntity(int id) {
        this.id = id;
    }

    public ItemEntity(int id, int version, String name, int price, int quantity) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public boolean beforeInsert(EntityManager em) {
        List<CategoryEntity> list = new ArrayList<>(categories);
        categories.clear();
        list.forEach(category -> {
            CategoryEntity found = em.find(category.getClass(), category.getId());
            found.items.add(this);
            categories.add(found);
        });
        return true;
    }

    @Override
    public boolean beforeDelete(EntityManager em) {
        categories.forEach(category -> {
            category.items.remove(this); // removes all foreign key references
        });
        return true;
    }

    @Override
    public void update(EntityManager em, EntityInt fromEntity) {
        // Displays the categories as seen inside the database
        System.out.println("ItemEntity__Before: " + categories.toString());
        ItemEntity source = (ItemEntity) fromEntity;
        // New set of categories
        final List<CategoryEntity> newCategories = new ArrayList<>(source.categories);
        // Displays the new set of categories we want to use (Should also exist inside the database)
        System.out.println("ItemEntity__Source: " + newCategories.toString());

        this.name = source.name;
        this.price = source.price;
        this.quantity = source.quantity;

        // this part triggers a rollback exception
        this.categories = source.categories;

        // 1: Need to remove this item from the categories of before
        this.categories.forEach(oldCategory -> {
            boolean keepCategory = false;
            for (CategoryEntity newCategory : newCategories) {
                if (oldCategory.id == newCategory.id) {
                    keepCategory = true;
                }
            }
            if (!keepCategory) {
                this.categories.remove(oldCategory);
            }
        });
        // 2: Need to add this item to any newly added categories
    }

    @Override
    public Query createVerifyIsUniqueQuery(EntityManager em) {
        return null;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "ItemEntity{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}