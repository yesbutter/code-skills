package DecorationMode;

import DecorationMode.Condiment.Mocha;
import DecorationMode.Condiment.Whip;
import DecorationMode.Drink.DrinkRoast;
import DecorationMode.Drink.Espresso;

public class Main {

    public static void main(String[] args) {
        Beverage beverage=new Espresso();
        System.out.println(beverage.getDescription()+" $"+beverage.cost());

        //易于扩展
        Beverage beverage1=new DrinkRoast();
        beverage1=new Mocha(beverage1); //装饰第一次
        beverage1=new Mocha(beverage1); //装饰第二次
        beverage1=new Whip(beverage1); //装饰第三次
        System.out.println(beverage1.getDescription()+" $"+beverage1.cost());


        Beverage beverage2=new Espresso();
        beverage2=new Whip(beverage2);
        beverage2=new Whip(beverage2);
        System.out.println(beverage2.getDescription()+" $"+beverage2.cost());

        //实际在Java中也有很多应用 比如io流
    }
}
