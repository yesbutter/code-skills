package Demo;

import Demo.Machine.GumBallMonitor;
import Demo.Machine.GumballMachine;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Service {

    private GumballMachine gumballMachine;

    private String description;
    public Service(String name, String description, int count) throws RemoteException, MalformedURLException {
        gumballMachine = new GumballMachine(name,count);
        Naming.rebind(description,gumballMachine);
        this.description=description;
    }


    public void service() throws RemoteException, MalformedURLException, InterruptedException {

        for (int i = 0;i < 10;i++)
        {
            Thread.sleep(1);
            gumballMachine.insertQuarter();
            gumballMachine.turnCrank();
            gumballMachine.insertQuarter();
            gumballMachine.turnCrank();
            gumballMachine.insertQuarter();
            gumballMachine.turnCrank();
        }

        try {
            Naming.unbind(description);
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

}
