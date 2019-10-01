package model.repository.entities;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Categories")
@NamedQueries({
        @NamedQuery(name = "Category.findAll", query = "SELECT c FROM  CategoryEntity c"),
        @NamedQuery(name = "Category.findById", query = "SELECT c FROM  CategoryEntity c WHERE c.id = :id"),
        @NamedQuery(name = "Category.findByName", query = "SELECT c FROM  CategoryEntity c WHERE c.name = :name"),
        @NamedQuery(name = "Category.deleteById", query = "DELETE FROM CategoryEntity c WHERE c.id =:id"),
})
public class CategoryEntity  implements EntityInt {

    @Id
    @GeneratedValue(generator = "incrementor")
    @Column(name = "category_id", unique = true)
    public int id;

    @Version
    public int version;

    @Column(name = "name", nullable = false, unique = true)
    public String name;

    @ManyToMany( cascade = {
            CascadeType.PERSIST, CascadeType.MERGE
    })
    @JoinTable(name = "CategoryItems", joinColumns = { @JoinColumn(name = "category_id") }, inverseJoinColumns = { @JoinColumn(name = "item_id") })
    public Set<ItemEntity> items = new HashSet<>();

    public CategoryEntity() { }

    public CategoryEntity(int id) {
        this.id = id;
    }

    public CategoryEntity(String name) {
        this.name = name;
    }

    @Override
    public boolean onDelete(EntityManager em) {
        items.forEach(item -> {
            item.categories.remove(this); // removes all foreign key references
        });
        return true;
    }

    @Override
    public boolean onInsert(EntityManager em) {
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
        CategoryEntity dest = (CategoryEntity) toEntity;
        dest.name = this.name;
    }

    @Override
    public Query createVerifyIsUniqueQuery(EntityManager em) {
        return em.createNamedQuery("Category.findByName")
                .setParameter("name", name);
    }

    public Query createFindAllQuery(EntityManager em) {
        return em.createNamedQuery("Category.findAll");
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ItemEntity> getItems() {
        return items;
    }

    public void setItems(Set<ItemEntity> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", items=" + items +
                '}';
    }
}
