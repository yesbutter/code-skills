package ObserverMode.Base;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ConcreteSubject extends Subject {

    public ConcreteSubject() {
        this.observersList = new ArrayList<>();
    }

    //    Observable
    @Override
    public synchronized void registerObserver(Observer observer) {
        if (observer == null)
            throw new NullPointerException("observer is null");
        if (!observersList.contains(observer))
            observersList.add(observer);
    }

    @Override
    public synchronized void removeObserver(Observer observer) {
        observersList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        notifyObservers(null);
    }

    public void notifyObservers(Object arg) {
        //TODO remove setChange
        setChange(true);
        Object[] arrLocal;
        synchronized (this)
        {
            if (!change)
                return;
            arrLocal=observersList.toArray();
            setChange(false);
        }

        //了解为什么是倒序
        for (int i = arrLocal.length-1; i>=0; i--)
            ((Observer)arrLocal[i]).update(this, arg);
    }


    public synchronized void setChange(Boolean change) {
        this.change = change;
    }
}
