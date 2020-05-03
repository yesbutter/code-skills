package FactoryMode.SimapleFactory;

/**
 * 负责加工pizza
 */
public class PizzaStore {

    PizzaFactory pizzaFactory;
    public PizzaStore(PizzaFactory pizzaFactory)
    {
        this.pizzaFactory=pizzaFactory;
    }

    public Pizza orderPizza(String type)
    {
        Pizza pizza=pizzaFactory.orderPizza(type);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }
}
