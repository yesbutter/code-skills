package Wrong;

public class HouseBlend extends Coffee {

    private static final double COST = 0.5;

    @Override
    public double cost() {
        return super.cost() + COST;
    }
}
