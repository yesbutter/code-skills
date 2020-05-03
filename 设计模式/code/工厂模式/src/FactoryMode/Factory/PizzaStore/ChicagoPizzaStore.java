package FactoryMode.Factory.PizzaStore;

import FactoryMode.Factory.Pizza.ChicagoStyleChinesePizza;
import FactoryMode.Factory.Pizza.ChicagoStyleVeggiePizza;
import FactoryMode.Factory.Pizza.Pizza;

public class ChicagoPizzaStore extends PizzaStore {


    @Override
    public Pizza createPizza(String type) throws ClassNotFoundException {
        Pizza pizza;
        switch (type) {
            case "Chinese":
                pizza = new ChicagoStyleChinesePizza();
                break;
            case "veggie":
                pizza = new ChicagoStyleVeggiePizza();
                break;
            default:
                throw new ClassNotFoundException("can not found " + type + "pizza");
        }
        return pizza;
    }
}
