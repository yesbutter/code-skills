package Wrong;

public class Main {

    public static void main(String[] args) {


        //如果想扩展咖啡更多产品的东西，这种设计模式修改起来就会更加复杂
        Coffee coffee=new DarkRoast();
        System.out.println(coffee.cost());
    }
}
