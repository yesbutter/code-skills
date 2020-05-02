package ObserverMode.Base;

public class Main {

    public static void main(String[] args) {
        Subject myObserver=new ConcreteSubject();
        Observer observer1=new ConcreteObserver();
        Observer observer2=new ConcreteObserver();
        Observer observer3=new ConcreteObserver();
        Observer observer4=new ConcreteObserver();
        myObserver.registerObserver(observer1);
        myObserver.registerObserver(observer2);
        myObserver.registerObserver(observer3);
        myObserver.registerObserver(observer4);

        myObserver.notifyObservers("hello observer");

        myObserver.removeObserver(observer1);
        myObserver.removeObserver(observer2);
        myObserver.removeObserver(observer3);
        myObserver.removeObserver(observer4);
    }

}
