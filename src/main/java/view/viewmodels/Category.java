package view.viewmodels;

public class Category {

    private int id = -1;
    private String name = null;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }
}
