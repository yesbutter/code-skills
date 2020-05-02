package Right.Duck;

import Right.Behavior.Fly.FlyBehavior;
import Right.Behavior.Fly.FlyWithWings;
import Right.Behavior.Quack.DuckQuack;
import Right.Behavior.Quack.QuackBehavior;

public class Duck {

    FlyBehavior flyBehavior;
    QuackBehavior quackBehavior;

    public Duck() {
        flyBehavior=new FlyWithWings();
        quackBehavior=new DuckQuack();
    }

    public Duck(FlyBehavior flyBehavior, QuackBehavior quackBehavior) {
        this.flyBehavior = flyBehavior;
        this.quackBehavior = quackBehavior;
    }

    public void swim()
    {
        System.out.println("i can swim");
    }

    public void display()
    {
        System.out.println("i am display");
    }

    public FlyBehavior getFlyBehavior() {
        return flyBehavior;
    }

    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    public QuackBehavior getQuackBehavior() {
        return quackBehavior;
    }

    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }
}
