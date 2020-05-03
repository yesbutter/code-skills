package CommandMode;

public class SimpleRemoteControl {

    Command slot;

    public SimpleRemoteControl(){}

    public void setCommend(Command command)
    {
        slot=command;
    }

    public void buttonWasPressed()
    {
        slot.execute();
    }
}
