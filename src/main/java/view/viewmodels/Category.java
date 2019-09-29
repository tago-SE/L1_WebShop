package view.viewmodels;

public class Category {

    private int id = -1;
    private String name = null;
    private int version;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(int id, String name, int version) {
        this.id = id;
        this.name = name;
        this.version = version;
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

    public int getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", version=" + version +
                '}';
    }
}
