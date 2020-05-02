package Wrong;

public class WeatherData {
    private double temperature;
    private double humidity;
    private double pressure;
    private DataWatcher1 dataWatcher1=new DataWatcher1();
    private DataWatcher2 dataWatcher2=new DataWatcher2();
    private DataWatcher3 dataWatcher3=new DataWatcher3();

    public void measureDataChanged()
    {
        temperature=Math.random();
        humidity=Math.random();
        pressure=Math.random();

        //每次我都需要去告诉他们让他们改变
        dataWatcher1.update(temperature,humidity,pressure);
        dataWatcher2.update(temperature,humidity,pressure);
        dataWatcher3.update(temperature,humidity,pressure);
    }


}
