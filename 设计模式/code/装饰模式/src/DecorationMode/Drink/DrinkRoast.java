package DecorationMode.Drink;

import DecorationMode.Beverage;

public class DrinkRoast extends Beverage {
    public DrinkRoast() {
        description="drink roast";
    }

    @Override
    public double cost() {
        return 0.8;
    }
}
