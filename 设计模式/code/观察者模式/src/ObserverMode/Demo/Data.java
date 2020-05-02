package ObserverMode.Demo;

public class Data {

    private double temperature;
    private double humidity;
    private double pressure;

    public Data(double temperature, double humidity, double pressuer) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressuer;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
}
