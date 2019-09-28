package model.repository.entities;

import javax.persistence.*;

//@Entity
//@Table(name = "Carts")
public class PurchaseEntity implements EntityInt {

    @Id
    @GeneratedValue(generator = "incrementor")
    @Column(name = "id", unique = true)
    public int id;

    @Column(name = "amount")
    public int amount;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
