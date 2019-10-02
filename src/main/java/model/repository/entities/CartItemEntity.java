package model.repository.entities;

import javax.persistence.*;

/*
@Entity
@Table(name = "CartItem")
public class CartItemEntity implements EntityInt {

    @Id
    @GeneratedValue(generator = "incrementor")
    @Column(name = "cart_item_id", unique = true)
    public int id;

    @Version
    public int version;

    @Column
    public int amount;

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "item_id", nullable = false )
    public ItemEntity item;

    public CartItemEntity() { }

    public CartItemEntity(ItemEntity item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    @Override
    public boolean beforeInsert(EntityManager em) {
        if (item != null)
            item.orderItems.add(this);
        return true;
    }

    @Override
    public boolean beforeDelete(EntityManager em) {
        if (item != null)
            item.orderItems.remove(this);
        return true;
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

    @Override
    public String toString() {
        return "OrderItemEntity{" +
                "id=" + id +
                ", version=" + version +
                ", amount=" + amount +
                ", item=" + item +
                '}';
    }
}
 */
