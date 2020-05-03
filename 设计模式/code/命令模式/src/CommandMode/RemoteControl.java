package CommandMode;

import java.util.ArrayList;
import java.util.List;

public class RemoteControl {

    private List<Command> onCommandList;
    private List<Command> offCommandList;
    private Command undoCommand;


    public RemoteControl() {
        this.onCommandList = new ArrayList<>();
        this.offCommandList = new ArrayList<>();
        Command command;
        for (int i = 0; i < 5; i++) {
            command = new NoCommand();
            onCommandList.add(command);
            command = new NoCommand();
            offCommandList.add(command);
        }
        undoCommand=new NoCommand();
    }


    public boolean onButtonPressed(int slot)
    {
        if(slot<getSize()) {
            undoCommand=onCommandList.get(slot);
            undoCommand.execute();
            return true;
        }
        return false;
    }

    public boolean offButtonPressed(int slot)
    {
        if(slot<getSize()) {
            undoCommand=offCommandList.get(slot);
            undoCommand.execute();
            return true;
        }
        return false;
    }


    public RemoteControl(List<Command> onCommandList, List<Command> offCommandList) {
        this.onCommandList = onCommandList;
        this.offCommandList = offCommandList;
    }



    public void undoButtonPressed()
    {
        undoCommand.undo();
    }
    /**
     * pos must lower than size
     * @param slot 插入位置
     * @param onCommand 打开命令
     * @param offCommand 关闭命令
     * @return 执行是否成功
     */
    public boolean setCommand(int slot, Command onCommand, Command offCommand) {
        if (slot < getSize())
        {
            onCommandList.set(slot,onCommand);
            offCommandList.set(slot,offCommand);
            return true;
        }
        return false;
    }

    public int getSize() {
        return onCommandList.size();
    }

}
