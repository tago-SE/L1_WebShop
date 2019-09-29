package model.repository.entities;

import javax.persistence.*;

//@Entity
//@Table(name = "Carts")
public class PurchaseEntity implements EntityInt {

    @Id
    @GeneratedValue(generator = "incrementor")
    @Column(name = "id", unique = true)
    public int id;

    @Version
    public int version;

    @Column(name = "amount")
    public int amount;

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

}
