package ObserverMode.Demo;

import ObserverMode.Base.Subject;

public class Main {

    public static void main(String[] args) {

        Subject weatherData=new WeatherData();
        DataWatcher1 watcher1=new DataWatcher1();
        DataWatcher2 watcher2=new DataWatcher2();

        weatherData.registerObserver(watcher1);
        weatherData.registerObserver(watcher2);

        weatherData.notifyObservers(new Data(35,10,20));

        weatherData.removeObserver(watcher1);
        weatherData.removeObserver(watcher2);

    }
}
