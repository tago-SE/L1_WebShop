package db.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Users")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM  UserEntity u"),
    @NamedQuery(name = "User.findByName", query = "SELECT u FROM  UserEntity u WHERE u.name = :name"),
    @NamedQuery(name = "User.findByNameContains", query = "SELECT u FROM UserEntity u WHERE u.name LIKE :search"),
    @NamedQuery(name = "User.validateCredentials", query = "SELECT u FROM  UserEntity u WHERE u.name = :name and u.password = :password")
})
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "incrementor")
    @Column(name = "id", unique = true)
    Integer id;

    @Column(name = "name", nullable = false, unique = true)
    String name;

    @Column(name = "pass", nullable = false)
    String password;

    public UserEntity() {}

    public UserEntity(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
