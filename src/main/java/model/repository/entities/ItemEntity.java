package model.repository.entities;

import javax.persistence.*;

@Entity
@Table(name = "Items")
public class ItemEntity implements EntityInt {

    @Id
    @GeneratedValue(generator = "incrementor")
    @Column(name = "id", unique = true)
    public int id;

    @Column(name = "name", unique = true, nullable = false)
    public String name;

    @Column(name = "price", nullable = false)
    public Integer price;

    @Column(name = "quantity", nullable = false)
    public Integer quantity;

    @Column(name = "category")
    public String category;

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
        return "ItemEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                '}';
    }

}