package model.repository.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/*
@Entity
@Table(name = "Carts")
public class UserCartEntity implements EntityInt {

    @Id
    public int id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    public UserEntity user;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    public Set<CartItemEntity> cartItems = new HashSet<>();

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
        return 0;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public Query createVerifyIsUniqueQuery(EntityManager em) {
        return null;
    }

    @Override
    public String toString() {
        return "UserCartEntity{" +
                "id=" + id +
                ", user_id=" + user.id +
                ", orderItems=" + cartItems +
                '}';
    }
}

 */
