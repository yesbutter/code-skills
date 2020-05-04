package Demo.Machine;

import Demo.Remote.GumBallMachineRemote;

import java.io.*;

public class GumBallMonitor {
    File file = new File("report.txt");
    FileWriter fileWriter;

    {
        try {
            fileWriter = new FileWriter(file.getName(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    GumBallMachineRemote machine;

    public GumBallMonitor(GumBallMachineRemote machine) {
        this.machine = machine;
    }

    public void report() {
        try {
            fileWriter.append("Gumball Machine:" + machine.getLocation() + "\n" +
                    "Gumball inventory:" + machine.getCount() + " gumballs" + "\n" + "\"Gumball state:\"+machine.getState()" + "\n");
            System.out.println("Gumball Machine:" + machine.getLocation());
            System.out.println("Gumball inventory:" + machine.getCount() + " gumballs");
            System.out.println("Gumball state:" + machine.getState());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
