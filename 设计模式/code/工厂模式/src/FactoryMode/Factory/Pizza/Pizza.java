package FactoryMode.Factory.Pizza;

public abstract class Pizza {

    private String name;
    private String toppings;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getToppings() {
        return toppings;
    }

    public void setToppings(String toppings) {
        this.toppings = toppings;
    }

    public void prepare()
    {
        System.out.println(this+":prepare");
    }
    public void bake() {
        System.out.println(this + ":bake");
    }

    public void cut() {
        System.out.println(this + ":cut");
    }

    public void box() {
        System.out.println(this + ":box");
    }

    @Override
    public String toString() {
        return hashCode()+" Pizza{" +
                "name='" + name +
                '}';
    }
}
