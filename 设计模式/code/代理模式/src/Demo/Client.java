package Demo;

import Demo.Machine.GumBallMonitor;
import Demo.Remote.GumBallMachineRemote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Client {


    public static void client(CountDownLatch countDownLatch) {
        List<String> locations = new ArrayList<>();
        locations.add("rmi://localhost/RemoteBeijing");
        locations.add("rmi://localhost/RemoteHangzhou");
        locations.add("rmi://localhost/RemoteZhengzhou");
        List<GumBallMonitor> monitors = new ArrayList<>();

        for (String location : locations) {
            try {
                GumBallMachineRemote machine = (GumBallMachineRemote) Naming.lookup(location);
                GumBallMonitor monitor = new GumBallMonitor(machine);
                System.out.println(monitor);
                monitors.add(monitor);
            } catch (RemoteException | MalformedURLException | NotBoundException e) {
                e.printStackTrace();
            }
        }

        while (countDownLatch.getCount() != 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (GumBallMonitor monitor : monitors)
                monitor.report();
        }

        for (GumBallMonitor monitor : monitors)
            monitor = null;
    }


}
