package Demo.Staute;

import Demo.Machine.GumballMachine;

import java.util.Random;

public class HasQuarterState implements State {

    Random randomWinner = new Random(System.currentTimeMillis());
    //保证machine不在jvm上序列化
    transient GumballMachine machine;


    public HasQuarterState(GumballMachine machine) {
        this.machine = machine;
    }

    @Override
    public void insertQuarter() {
        System.out.println("You can not insert another quarter");
    }

    @Override
    public void ejectQuarter() {
        System.out.println("Quarter returned");
        machine.setState(machine.getNoQuarterState());
    }

    @Override
    public void turnQuarter() {
        System.out.println("You turned...");
        int winner = randomWinner.nextInt(3);
        if (winner == 0 && machine.getCount() > 1)
            machine.setState(machine.getWinnerState());
        else
            machine.setState(machine.getSoldState());
    }

    @Override
    public void dispense() {
        System.out.println("No GumBall dispensed");
    }
}
