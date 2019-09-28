package model;

public class User {

    private int id;
    private String name;

    public User() {}

    public User(String name) {
        this.name = name;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "model.User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
