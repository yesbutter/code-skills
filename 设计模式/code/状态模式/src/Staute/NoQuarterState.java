package Staute;

import Machine.GumballMachine;

public class NoQuarterState implements State {

    GumballMachine machine;


    public NoQuarterState(GumballMachine machine) {
        this.machine = machine;
    }

    @Override
    public void insertQuarter() {
        System.out.println("You insert a quarter");
        machine.setState(machine.getHasQuarterState());
    }

    @Override
    public void ejectQuarter() {
        System.out.println("You have not insert a quarter");
    }

    @Override
    public void turnQuarter() {
        System.out.println("You turned,but the there is no quarter");
    }

    @Override
    public void dispense() {
        System.out.println("You need to pay first");
    }
}
