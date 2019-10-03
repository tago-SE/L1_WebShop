package view.viewmodels;

import java.util.Date;
import java.util.List;

public class Order {

    public final int id;
    public final int version;
    public final String status;
    public final Date sent;
    public final Date delivered;
    public final User user;
    public final int cost;
    public final List<OrderItem> orderItems;

    public Order(int id, int ver, User u, String status, Date sent, Date delivered, List<OrderItem> orderItems) {
        this.id = id;
        this.version = ver;
        this.user = u;
        this.status = status;
        this.sent = sent;
        this.delivered = delivered;
        this.orderItems = orderItems;
        int total = 0;
        for (OrderItem oi : orderItems) {
            total = oi.cost;
        }
        this.cost = total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", version=" + version +
                ", status='" + status + '\'' +
                ", sent=" + sent +
                ", delivered=" + delivered +
                ", user=" + user +
                ", orderItems=" + orderItems +
                '}';
    }
}
