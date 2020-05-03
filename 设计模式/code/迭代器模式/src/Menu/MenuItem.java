package Menu;

public class MenuItem {


    private String name;

    public MenuItem(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "name='" + name + '\'' +
                '}';
    }
}
