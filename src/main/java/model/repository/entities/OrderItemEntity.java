package model.repository.entities;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class OrderItemEntity implements EntityInt {

    public int id;
    public int version;
    public ItemEntity item;
    public int quantity;

    @Override
    public boolean beforeInsert(EntityManager em) {
        return false;
    }

    @Override
    public boolean beforeDelete(EntityManager em) {
        return false;
    }

    @Override
    public void update(EntityManager em, EntityInt toEntity) {

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
    public Query createVerifyIsUniqueQuery(EntityManager em) {
        return null;
    }
}
