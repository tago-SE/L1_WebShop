package view.viewmodels;

public class Category {

    private final int id;
    private final String name;
    private final int version;

    public Category() {
        id = version = 0;
        name = null;
    }

    public Category(String name) {
        this.name = name;
        id = version = 0;
    }

    public Category(int id, String name) {
        this.name = name;
        this.id = id;
        version = 0;
    }

    public Category(int id, String name, int version) {
        this.id = id;
        this.name = name;
        this.version = version;
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
