package model.db.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class CategoryEntity  implements EntityInt {

    @Id
    @Column(name = "id", unique = true)
    Integer id;

    @Column(name = "name", nullable = false, unique = true)
    String name;

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
