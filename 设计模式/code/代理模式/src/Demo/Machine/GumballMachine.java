package Demo.Machine;

import Demo.Remote.GumBallMachineRemote;
import Demo.Staute.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GumballMachine extends UnicastRemoteObject implements GumBallMachineRemote {
    private State noQuarterState = null;
    private State hasQuarterState = null;
    private State soldOutState = null;
    private State soldState = null;
    private State winnerState = null;


    private State state = soldOutState;
    //糖果数量
    private int count = 0;
    private String location;

    @Override
    public State getState() {
        return state;
    }

    @Override
    public String getLocation() {
        return location;
    }

    public GumballMachine(String location, int count) throws RemoteException {
        noQuarterState = new NoQuarterState(this);
        hasQuarterState = new HasQuarterState(this);
        soldOutState = new SoldOutState(this);
        soldState = new SoldState(this);
        winnerState=new WinnerState(this);
        this.count = count;
        if (this.count > 0)
            state = noQuarterState;
        this.location=location;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void insertQuarter() {
        state.insertQuarter();
    }

    public void ejectQuarter() {
        state.ejectQuarter();
    }

    public void turnCrank() {
        state.turnQuarter();
        state.dispense();
    }

    public int getCount() {
        return count;
    }

    public void releaseBall() {
        if (count != 0) {
            System.out.println("A gumball comes rolling out the slot...");
            count--;
        }
    }

    public State getNoQuarterState() {
        return noQuarterState;
    }

    public State getHasQuarterState() {
        return hasQuarterState;
    }

    public State getSoldOutState() {
        return soldOutState;
    }

    public State getSoldState() {
        return soldState;
    }

    public State getWinnerState() {
        return winnerState;
    }
}
