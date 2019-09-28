package model.db.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Users")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM  UserEntity u"),
        @NamedQuery(name = "User.findByName", query = "SELECT u FROM  UserEntity u WHERE u.name = :name"),
        @NamedQuery(name = "User.findByNameContains", query = "SELECT u FROM UserEntity u WHERE u.name LIKE :search"),
        @NamedQuery(name = "User.validateCredentials", query = "SELECT u FROM  UserEntity u WHERE u.name = :name and u.password = :password")
})
public class UserEntity implements EntityInt {

    @Id
    @GeneratedValue(generator = "incrementor")
    @Column(name = "id", unique = true)
    public Integer id;

    @Column(name = "name", nullable = false, unique = true)
    public String name;

    @Column(name = "pass", nullable = false)
    public String password;


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
