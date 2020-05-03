package FactoryMode.Factory.Pizza;

public class ChicagoStyleVeggiePizza extends VeggiePizza {

    public ChicagoStyleVeggiePizza() {
        setName("ChicagoStyle" + super.getName());
    }
}
