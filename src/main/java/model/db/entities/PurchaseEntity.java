package model.db.entities;

import model.Item;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Carts")
public class PurchaseEntity implements EntityInt {

    @Id
    @GeneratedValue(generator = "incrementor")
    @Column(name = "id", unique = true)
    public Integer id;

    @OneToOne
    public ItemEntity item;

    @Column(name = "amount")
    public int amount;

}
