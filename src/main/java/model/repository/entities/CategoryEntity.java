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

    @Version
    public int version;

    @Column(name = "name", nullable = false, unique = true)
    public String name;

    public CategoryEntity() { }

    public CategoryEntity(int id) {
        this.id = id;
    }

    public CategoryEntity(String name) {
        this.name = name;
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

    @Override
    public Query createFindByIdQuery(EntityManager em) {
        return em.createNamedQuery("Category.findById")
                .setParameter("id", id);
    }

    public Query createFindAll(EntityManager em) {
        return em.createNamedQuery("Category.findAll");
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                '}';
    }
}
