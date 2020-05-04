package Machine;

import Staute.*;

public class GumballMachine {
    private State noQuarterState = null;
    private State hasQuarterState = null;
    private State soldOutState = null;
    private State soldState = null;
    private State winnerState = null;


    private State state = soldOutState;
    //糖果数量
    int count = 0;

    public GumballMachine(int count) {
        noQuarterState = new NoQuarterState(this);
        hasQuarterState = new HasQuarterState(this);
        soldOutState = new SoldOutState(this);
        soldState = new SoldState(this);
        winnerState=new WinnerState(this);
        this.count = count;
        if (this.count > 0)
            state = noQuarterState;
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
