package model.repository.entities;

import javax.persistence.*;

@Entity
@Table(name = "T_OrderItem")
public class OrderItemEntity implements EntityInt {

    @Id
    @GeneratedValue(generator = "incrementor")
    @Column(name = "order_item_id", unique = true)
    public int id;

    @Version
    public int version;

    @Column(nullable = false)
    public int amount;

    @Column(nullable = false)
    public int price_per_item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="item_id", nullable = false)
    private ItemEntity item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="order_id", nullable = false)
    private OrderEntity order;

    public OrderItemEntity() { }

    public OrderItemEntity(ItemEntity item, OrderEntity order, int amount, int price) {
        setItem(item);
        setOrder(order);
        this.amount = amount;
        this.price_per_item = price;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        if (this.item != null)
            this.item.orderItems.remove(this);
        this.item = item;
        this.item.orderItems.add(this);
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
        order.orderItems.add(this);
    }

    @Override
    public boolean beforeInsert(EntityManager em) {
        return true;
    }

    @Override
    public boolean beforeDelete(EntityManager em) {
        item.orderItems.remove(this);
        return true;
    }

    @Override
    public void update(EntityManager em, EntityInt fromEntity) {
        OrderItemEntity source = (OrderItemEntity) fromEntity;
        this.amount = source.amount;
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
