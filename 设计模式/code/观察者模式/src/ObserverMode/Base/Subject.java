package ObserverMode.Base;

import java.util.List;

public abstract class Subject {

    protected Boolean change=false;
    protected List<Observer> observersList;
    public abstract void registerObserver(Observer observer);
    public abstract void removeObserver(Observer observer);
    public abstract void notifyObservers();
    public abstract void notifyObservers(Object obj);
}
