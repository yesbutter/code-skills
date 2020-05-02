package Wrong;

public class DataWatcher1 {

    private double temperature;
    private double humidity;
    private double pressure;


    public void update(double temperature,double humidity,double pressure)
    {
        this.temperature=temperature;
        this.humidity=humidity;
        this.pressure=pressure;
    }

}
