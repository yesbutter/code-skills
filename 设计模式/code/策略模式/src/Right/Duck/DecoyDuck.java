package Right.Duck;

import Right.Behavior.Fly.CanNotFly;
import Right.Behavior.Quack.DuckQuack;

public class DecoyDuck extends Duck{

    public DecoyDuck() {
        flyBehavior=new CanNotFly();
        quackBehavior=new DuckQuack();
    }

    @Override
    public void display() {
        System.out.println("i am decoy duck");
    }
}
