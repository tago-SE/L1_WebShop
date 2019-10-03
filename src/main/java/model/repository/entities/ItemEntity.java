package model.repository.entities;

import view.viewmodels.Category;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "T_Item")
@NamedQueries({
        @NamedQuery(name = "Item.findAll", query = "SELECT i FROM  ItemEntity i"),
        @NamedQuery(name = "Item.findByName", query = "SELECT i FROM  ItemEntity i WHERE i.name = :name"),
})
public class ItemEntity implements EntityInt {

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

    @OneToMany(mappedBy="item")
    public Set<OrderItemEntity> orderItems = new HashSet<>();

    public ItemEntity() { }

    public ItemEntity(int id) {
        this.id = id;
    }

    public ItemEntity(String name, int price, int quantity) {
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
        //orderItems.forEach(em::remove);
        return true;
    }

    @Override
    public void update(EntityManager em, EntityInt fromEntity) {
        ItemEntity source = (ItemEntity) fromEntity;
        this.name = source.name;
        this.price = source.price;
        this.quantity = source.quantity;

        // Removes any relations that no longer exists between Categories and Items
        //
        List<CategoryEntity> addedList = new ArrayList<>();
        List<CategoryEntity> removedList = new ArrayList<>();
        for (CategoryEntity newC : source.categories) {
            if (!categories.contains(newC)) {
                addedList.add(newC);
            }
        }
        for (CategoryEntity oldC : categories) {
            if (!source.categories.contains(oldC)) {
                removedList.add(oldC);
            }
        }
        for (CategoryEntity c : removedList) {
            categories.remove(c);
            c.items.remove(this);
        }
        for (CategoryEntity c : addedList) {
            CategoryEntity persistentCategory = em.find(CategoryEntity.class, c.id);
            categories.add(persistentCategory);
            persistentCategory.items.add(this);
        }
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
        StringBuilder sb = new StringBuilder();
        for (CategoryEntity c : categories) {
            sb.append(c.name).append(",");
        }
        String categoriesStr = sb.toString();
        return "ItemEntity{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ",{" + categoriesStr.substring(0, categoriesStr.length() - 1) + "}" +
                '}';
    }
}