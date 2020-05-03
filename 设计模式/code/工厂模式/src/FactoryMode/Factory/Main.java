package FactoryMode.Factory;

import FactoryMode.Factory.Pizza.Pizza;
import FactoryMode.Factory.PizzaStore.ChicagoPizzaStore;
import FactoryMode.Factory.PizzaStore.NYPizzaStore;
import FactoryMode.Factory.PizzaStore.PizzaStore;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        PizzaStore pizzaStore = new ChicagoPizzaStore();
        PizzaStore pizzaStore1 = new NYPizzaStore();
        Pizza pizza = pizzaStore.orderPizza("veggie");
        /**
         * 当我们业务跨站时候，需要不同类型的pizza,就需要对原来的工厂进行修改，
         * 这样不利于后期维护
         */
        Pizza pizza1 = pizzaStore1.orderPizza("Chinese");
    }
}
