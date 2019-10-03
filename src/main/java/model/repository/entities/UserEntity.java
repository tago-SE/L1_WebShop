package model.repository.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.*;

@Entity
    @Table(name = "T_User")
    @NamedQueries({
            @NamedQuery(name = "User.findAll", query = "SELECT u FROM  UserEntity u"),
            @NamedQuery(name = "User.findByName", query = "SELECT u FROM  UserEntity u WHERE u.name = :name"),
            @NamedQuery(name = "User.findByNameContains", query = "SELECT u FROM UserEntity u WHERE u.name LIKE :search"),
            @NamedQuery(name = "User.validateCredentials", query = "SELECT u FROM  UserEntity u WHERE u.name = :name and u.password = :password")
    })
public class UserEntity implements EntityInt {

    @Id
    @GeneratedValue(generator = "incrementor")
    @Column(name = "user_id", unique = true)
    public int id;

    @Version
    public int version;

    @Column(name = "name", nullable = false, unique = true)
    public String name;

    @Column(name = "pass", nullable = false)
    public String password;

    @Column(name = "fname")
    public String firstName;

    @Column(name ="lname")
    public String lastName;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="T_UserAccess", joinColumns=@JoinColumn(name="user_id"))
    @Column(name="access")
    public Set<String> accessRoles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    public Set<OrderEntity> orders = new HashSet<>();

    public UserEntity() {}

    public UserEntity(int id) {
        this.id = id;
    }

    public UserEntity(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void addAccess(String access) {
        if (access == null)
            return;
        if (!accessRoles.contains(access)) {
            accessRoles.add(access);
        }
    }

    public void removeAccess(String access) {
        accessRoles.remove(access);
    }

    @Override
    public boolean beforeInsert(EntityManager em) {
        return true;
    }

    @Override
    public boolean beforeDelete(EntityManager em) {
        return true;
    }

    @Override
    public void update(EntityManager em, EntityInt fromEntity) {
        UserEntity source = (UserEntity) fromEntity;
        this.accessRoles = source.accessRoles;
        this.name = source.name;
        this.firstName = source.firstName;
        this.lastName = source.lastName;
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
        return em.createNamedQuery("User.findByName").setParameter("name", name);
    }

    public Query createValidateCredentials(EntityManager em) {
        return em.createNamedQuery("User.validateCredentials")
                .setParameter("name", name)
                .setParameter("password", password);
    }

    public Query createFindAll(EntityManager em) {
        return em.createNamedQuery("User.findAll");
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", fname='" + firstName + '\'' +
                ", lname='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", accessRoles=" + accessRoles +
                '}';
    }

}
