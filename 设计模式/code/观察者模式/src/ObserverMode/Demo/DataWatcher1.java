package ObserverMode.Demo;

import ObserverMode.Base.Observer;
import ObserverMode.Base.Subject;

public class DataWatcher1 implements Observer {

    private double temperature;
    private double humidity;
    private double pressure;



    @Override
    public <T extends Subject> void update(T subject, Object arg) {
        if(arg instanceof Data)
        {
            Data data= (Data) arg;
            this.temperature=data.getTemperature();
            this.humidity=data.getHumidity();
            this.pressure=data.getPressure();
        }
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "temperature:"+temperature+"\n"+"humidity:"+humidity+"\n"+"pressure:"+pressure;
    }
}
