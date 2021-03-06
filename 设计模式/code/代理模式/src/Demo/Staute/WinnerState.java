package Demo.Staute;

import Demo.Machine.GumballMachine;

public class WinnerState implements State {
    transient GumballMachine machine;


    public WinnerState(GumballMachine machine) {
        this.machine = machine;
    }

    @Override
    public void insertQuarter() {
        System.out.println("Please wait,we are already giving you a gumball");
    }

    @Override
    public void ejectQuarter() {
        System.out.println("Sorry,you already turned the crank");
    }

    @Override
    public void turnQuarter() {
        System.out.println("Turning twice does not get you another gumball");
    }

    @Override
    public void dispense() {
        if (machine.getCount() > 2)
            System.out.println("You are a winner!You get two gumballs for your quarter");
        machine.releaseBall();
        if(machine.getCount()==0)
        {
            System.out.println("Oops,out of gumballs!");
            machine.setState(machine.getSoldOutState());
        }
        else  {
            machine.releaseBall();
            if(machine.getCount()>0)
            {
                machine.setState(machine.getNoQuarterState());
            }
            else {
                System.out.println("Oops,out of gumballs!");
                machine.setState(machine.getSoldOutState());
            }
        }
    }
}
