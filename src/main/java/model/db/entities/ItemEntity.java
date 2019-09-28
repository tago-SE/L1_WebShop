package model.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Items")
public class ItemEntity implements EntityInt {

    @Id
    @Column(name = "id", unique = true)
    Integer id;

    @Column(name = "name", unique = true, nullable = false)
    String name;

    @Column(name = "price", nullable = false)
    Integer price;

    @Column(name = "quantity", nullable = false)
    Integer quantity;

    @Column(name = "category")
    String category;


    @Override
    public String toString() {
        return "ItemEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                '}';
    }
}