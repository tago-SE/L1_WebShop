package model.repository.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Items")
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

    @ManyToMany(mappedBy="items", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<CategoryEntity> categories = new HashSet<>();

    /*
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="ItemCategories", joinColumns=@JoinColumn(name="item_id"))
    @Column(name="category")
    public Set<String> categories = new HashSet<>();
    */

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
    public boolean onInsert() {
        return true;
    }

    @Override
    public boolean onDelete(EntityManager em) {
        categories.forEach(category -> {
            category.items.remove(this); // removes all foreign key references
        });
        return true;
    }

    @Override
    public boolean onUpdate() {
        return true;
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
    public void transferTo(EntityInt toEntity) {

    }

    @Override
    public Query createVerifyIsUniqueQuery(EntityManager em) {
        return null;
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