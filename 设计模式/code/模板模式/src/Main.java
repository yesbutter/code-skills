import Template.CaffeineBeverage;
import Template.Coffee;
import Template.Tea;

public class Main {

    public static void main(String[] args) {
        /*
          常见的模板模式 ，在Java集合类中的排序
          模板模式会在指定的时间调用方法
         */
        CaffeineBeverage drink=new Tea();
        drink.prepareRecipe();
        System.out.println("--------------");
        drink=new Coffee();
        drink.prepareRecipe();
    }
}
