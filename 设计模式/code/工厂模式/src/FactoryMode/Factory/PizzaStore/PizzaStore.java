package FactoryMode.Factory.PizzaStore;

import FactoryMode.Factory.Pizza.Pizza;

/**
 * 负责加工pizza
 */
public abstract class PizzaStore {


    public Pizza orderPizza(String type) throws ClassNotFoundException {
        Pizza pizza;
        pizza=createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }


    public abstract Pizza createPizza(String type) throws ClassNotFoundException;
}
