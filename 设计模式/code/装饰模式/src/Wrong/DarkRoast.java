package Wrong;

public class DarkRoast extends Coffee{


    @Override
    public double cost() {
        return super.cost()+0.3;
    }
}
