package Staute;

import Machine.GumballMachine;

public class SoldOutState implements State {

    GumballMachine machine;


    public SoldOutState(GumballMachine machine) {
        this.machine = machine;
    }
    @Override
    public void insertQuarter() {
        System.out.println("Sorry,There is no GumBall.");
    }

    @Override
    public void ejectQuarter() {
        System.out.println("Sorry,You can not eject.You have not inserted a quarter yet");
    }

    @Override
    public void turnQuarter() {
        System.out.println("You turned.But There is no GumBall");
    }

    @Override
    public void dispense() {
        System.out.println("No GumBall dispensed");

    }
}
