package FactoryMode.Factory.PizzaStore;

import FactoryMode.Factory.Pizza.*;

public class NYPizzaStore extends PizzaStore {


    @Override
    public Pizza createPizza(String type) throws ClassNotFoundException {
        Pizza pizza;
        switch (type)
        {
            case "Chinese":
                pizza=new NYStyleChinesePizza();
                break;
            case "veggie":
                pizza=new NYStyleVeggiePizza();
                break;
            default:
                throw new ClassNotFoundException("can not found "+type+"pizza");
        }
        return pizza;
    }

}
