package Right;

import Right.Behavior.Fly.CanNotFly;
import Right.Behavior.Quack.MuteQuack;
import Right.Duck.DecoyDuck;
import Right.Duck.Duck;
import Right.Duck.MallardDuck;

public class Main {

    public static void main(String[] args) {
        Duck duck=new DecoyDuck();
        duck.setFlyBehavior(new CanNotFly());
        duck.getFlyBehavior().fly();

        Duck duck1=new MallardDuck();
        duck1.setQuackBehavior(new MuteQuack());
        duck1.getQuackBehavior().quack();
    }
}
