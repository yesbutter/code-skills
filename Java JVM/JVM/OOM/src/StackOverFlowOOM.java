/**
 * HotSpot不支持动态扩展栈弓箭
 * 1.设置参数 -Xss128k 减少栈容量，不断调用
 * 2.大量的本地变量，增大栈帧的长度。
 *
 * 3.大量的创建线程，每个线程都会分配栈空间，栈空间越大，总容量固定，超出限制后就OOM
 */
public class StackOverFlowOOM {

    private int stackLength = 1;

    public void stackLeak()
    {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        StackOverFlowOOM oom=new StackOverFlowOOM();
        try {
            oom.stackLeak();
        }catch (Throwable e)
        {
            System.out.println("stack length:"+oom.stackLength);
            throw e;
        }
    }
}
