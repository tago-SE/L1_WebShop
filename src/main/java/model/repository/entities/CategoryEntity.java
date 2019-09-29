package model.repository.entities;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Categories")
@NamedQueries({
        @NamedQuery(name = "Category.findAll", query = "SELECT c FROM  CategoryEntity c"),
        @NamedQuery(name = "Category.findById", query = "SELECT c FROM  CategoryEntity c WHERE c.id = :id"),
        @NamedQuery(name = "Category.findByName", query = "SELECT c FROM  CategoryEntity c WHERE c.name = :name"),
        @NamedQuery(name = "Category.deleteById", query = "DELETE FROM CategoryEntity c WHERE c.id =:id")
})
public class CategoryEntity  implements EntityInt {

    @Id
    @GeneratedValue(generator = "incrementor")
    @Column(name = "category_id", unique = true)
    public int id;

    @Column(name = "name", nullable = false, unique = true)
    public String name;

    @Column(name = "ts")
    public Date timestamp;

    /*
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Category_Item",
            joinColumns = { @JoinColumn(name = "category_id") },
            inverseJoinColumns = { @JoinColumn(name = "item_id") }
    )
    */
    @Transient
    public List<ItemEntity> items = new ArrayList<>();

    public CategoryEntity() {
        this.timestamp = new Date();
    }

    public CategoryEntity(String name) {
        this.name = name;
        this.timestamp = new Date();
    }

    @Override
    public Query createVerifyIsUniqueQuery(EntityManager em) {
        return em.createNamedQuery("Category.findByName")
                .setParameter("name", name);
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", timestamp=" + timestamp +
                ", items=" + items +
                '}';
    }
}
