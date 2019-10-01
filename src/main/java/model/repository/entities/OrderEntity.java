package model.repository.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class OrderEntity {

    public int id;
    public int version;
    public String status;
    public Date dateRequest;
    public Date dateSent;
    public Set<OrderItemEntity> orderItems = new HashSet<>();

}
