package FactoryMode.Factory.Abstract;

public interface PizzaIngredientFactory {

    //使用工厂实现方法，完成原料的制作
    public Dough createDough();
    public Sauce createSauce();

}
