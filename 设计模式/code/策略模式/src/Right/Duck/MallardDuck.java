package Right.Duck;

import Right.Behavior.Fly.FlyWithWings;
import Right.Behavior.Quack.DuckQuack;

public class MallardDuck extends Duck{

    public MallardDuck() {
        super();
    }

    @Override
    public void display() {
        System.out.println("i am green head duck");
    }
}
