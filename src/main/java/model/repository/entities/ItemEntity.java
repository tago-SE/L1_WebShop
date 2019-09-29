package model.repository.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    public Integer price;

    @Column(name = "quantity", nullable = false)
    public Integer quantity;

    //@ManyToMany(mappedBy = "items")
    @Transient
    public List<CategoryEntity> categories = new ArrayList<>();

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
    public Query createFindByIdQuery(EntityManager em) {
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
                ", categories=" + categories +
                '}';
    }
}