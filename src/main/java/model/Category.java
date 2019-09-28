package model;

public class Category {

    private String name;

    public String getName() {
        return name;
    }

    public Category() { }

    public Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }
}
