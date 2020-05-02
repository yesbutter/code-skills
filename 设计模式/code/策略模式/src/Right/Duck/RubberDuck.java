package Right.Duck;

import Right.Behavior.Fly.CanNotFly;
import Right.Behavior.Quack.MuteQuack;

public class RubberDuck extends Duck {

    public RubberDuck() {
        flyBehavior=new CanNotFly();
        quackBehavior=new MuteQuack();
    }

    @Override
    public void display() {
        System.out.println("i am rubber duck");
    }
}
