package view.viewmodels;

public class OrderItem {

    public final int id;
    public final int version;
    public final Item item;
    public final int amount;
    public final int price;
    public final int cost;

    public OrderItem(int id, int version, Item item, int amount, int price) {
        this.id = id;
        this.version = version;
        this.item = item;
        this.amount = amount;
        this.price = price;
        this.cost = amount*price;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", version=" + version +
                ", item=" + item +
                ", amount=" + amount +
                ", price=" + price +
                ", cost=" + cost +
                '}';
    }
}
