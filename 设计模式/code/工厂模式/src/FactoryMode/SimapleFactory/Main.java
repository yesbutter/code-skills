package FactoryMode.SimapleFactory;

public class Main {

    public static void main(String[] args) {
        PizzaStore pizzaStore =new PizzaStore(new PizzaFactory());
        Pizza pizza=pizzaStore.orderPizza("veggie");

        /**
         * 当我们业务跨站时候，需要不同类型的pizza,就需要对原来的工厂进行修改，
         * 这样不利于后期维护
         */
        Pizza pizza1=pizzaStore.orderPizza("Chinese");
    }
}
