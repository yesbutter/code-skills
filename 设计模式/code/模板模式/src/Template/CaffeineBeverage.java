package Template;

public abstract class CaffeineBeverage {

    public final void prepareRecipe()
    {
        boilWater();
        brew();
        pourInCup();
        addCondiments();
    }

    void boilWater()
    {
        System.out.println("Boiling water");
    }

    abstract void brew();

    void pourInCup()
    {
        System.out.println("Pouring into cup");
    }

    abstract void addCondiments();
}
