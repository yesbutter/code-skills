package Demo.Remote;

import Demo.Staute.State;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GumBallMachineRemote extends Remote {

    public int getCount() throws RemoteException;

    public String getLocation() throws RemoteException;

    public State getState() throws RemoteException;
}
