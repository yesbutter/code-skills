package FactoryMode.Factory.Pizza;

public class NYStyleChinesePizza extends ChinesePizza {

    public NYStyleChinesePizza() {
        setName("NYStyle"+super.getName());
    }
}
