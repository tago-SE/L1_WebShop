package model.repository.entities;

import javax.persistence.*;
import java.util.Date;

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
    @Column(name = "id", unique = true)
    public int id;

    @Column(name = "name", nullable = false, unique = true)
    public String name;

    @Column(name = "ts")
    public Date timestamp;  // last modified

    public CategoryEntity() {
        this.timestamp = new Date();
    }

    public CategoryEntity(String name) {
        this.name = name;
        this.timestamp = new Date();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
