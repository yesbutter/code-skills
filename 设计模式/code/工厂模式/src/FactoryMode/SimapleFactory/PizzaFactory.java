package FactoryMode.SimapleFactory;

/**
 * 负责生产pizza
 */
public class PizzaFactory {

    public PizzaFactory(){};


    public Pizza orderPizza(String type)
    {
        Pizza pizza;
        switch (type)
        {
            case "Chinese":
                pizza=new ChinesePizza();
                break;
            case "veggie":
                pizza=new VeggiePizza();
                break;
            default:
                pizza=new Pizza();
                break;
        }

        return pizza;
    }

    public Pizza orderPizza()
    {
        Pizza pizza =new Pizza();

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }





}
