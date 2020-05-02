package Right.Behavior.Fly;

public class CanNotFly implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("i can not fly");
    }
}
