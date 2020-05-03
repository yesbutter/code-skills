package FactoryMode.Factory.Pizza;

public class NYStyleVeggiePizza extends VeggiePizza {

    public NYStyleVeggiePizza() {
        setName("NYStyle"+super.getName());
    }
}
