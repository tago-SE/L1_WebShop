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
        ItemEntity source = (ItemEntity) fromEntity;
        this.name = source.name;
        this.price = source.price;
        this.quantity = source.quantity;

        // Removes any relations that no longer exists between Categories and Items
        //
        final List<CategoryEntity> newCategories = new ArrayList<>(source.categories);
        categories.forEach(oldCategory -> {
            Iterator<CategoryEntity> itr = newCategories.iterator();
            boolean doRemove = true;
            while (itr.hasNext()) {
                CategoryEntity newCategory = itr.next();
                if (newCategory.id == oldCategory.id) {
                    // Remove from new category list those categories that have been retained
                    newCategories.remove(newCategory);
                    doRemove = false;
                    break;
                }
            }
            if (doRemove) {
                // Remove relation between item and category
                this.categories.remove(oldCategory);
                oldCategory.items.remove(this);
            }
        });
        // We fetch the persistent category for those that were added to not get any unattached errors.
        newCategories.forEach(category -> {
            // Add relation between item and category
            CategoryEntity persistentCategory = em.find(CategoryEntity.class, category.id);
            categories.add(persistentCategory);
            persistentCategory.items.add(this);
        });
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