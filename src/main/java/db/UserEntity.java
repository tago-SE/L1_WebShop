package db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Users")
public class UserEntity implements Serializable {

    @Id
    @Column(name = "id", unique = true)
    Integer id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "pass", nullable = false)
    String password;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
