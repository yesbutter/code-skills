package CommandMode;

public class Light {

    private boolean statue = false;

    public void on() {
        System.out.println("light on");
        statue = true;
    }

    public void off() {
        System.out.println("light off");
        statue = false;
    }

}
