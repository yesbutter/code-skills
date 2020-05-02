package ObserverMode.Base;

public class ConcreteObserver implements Observer {

    @Override
    public <T extends Subject> void update(T subject, Object arg) {
        System.out.println("i am "+this+" i receive "+subject+" message is "+arg);
    }
}
