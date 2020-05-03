import CommandMode.*;

public class Main {

    public static void main(String[] args) {
        SimpleRemoteControl remote=new SimpleRemoteControl();
        Light light=new Light();
        Command cmd=new LightOnCommand(light);
        remote.setCommend(cmd);
        //执行
        remote.buttonWasPressed();


        RemoteControl remoteControl=new RemoteControl();
        Light light1=new Light();
        Command onCMD=new LightOnCommand(light1);
        Command offCMD=new LightOffCommand(light1);
        remoteControl.setCommand(0,onCMD,offCMD);

        remoteControl.offButtonPressed(0);
        remoteControl.undoButtonPressed();
        remoteControl.onButtonPressed(0);
    }
}
