package FactoryMode.SimapleFactory;

public class Pizza {

    protected void prepare()
    {
        System.out.println(this+":prepare");
    }
    protected void bake() {
        System.out.println(this + ":bake");
    }

    protected void cut() {
        System.out.println(this + ":cut");
    }

    protected void box() {
        System.out.println(this + ":box");
    }
}
