package model.repository.entities;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class OrderEntity implements EntityInt {

    public int id;
    public int version;
    public String status;
    public Date dateRequest;
    public Date dateSent;
    public UserEntity user;
    public Set<OrderItemEntity> orderItems = new HashSet<>();

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
