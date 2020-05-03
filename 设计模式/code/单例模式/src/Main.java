import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        //可以看到打印的都是同一个对象
        //失败的单例会打印多个
        CountDownLatch latch=new CountDownLatch(100);
        List<FailedSingleton> failedSingletonVector=new Vector<>();
        List<SimpleSingleton> simpleSingletonVector=new Vector<>();
        List<SyncSingleton> syncSingletonVector=new Vector<>();
        List<ReferenceSingleton> referenceSingletonVector=new Vector<>();
        for (int i = 0; i < 100; i++)
            new Thread(() -> {
                FailedSingleton failedSingleton = FailedSingleton.getInstance();
                failedSingletonVector.add(failedSingleton);

                SimpleSingleton simpleSingleton = SimpleSingleton.getInstance();
                simpleSingletonVector.add(simpleSingleton);

                SyncSingleton syncSingleton=SyncSingleton.getInstance();
                syncSingletonVector.add(syncSingleton);

                ReferenceSingleton referenceSingleton = ReferenceSingleton.getInstance();
                referenceSingletonVector.add(referenceSingleton);
                latch.countDown();
            }).start();
        latch.await();
        System.out.println("FailedSingleton:"+judge(failedSingletonVector,100));
        System.out.println("SimpleSingleton:"+judge(simpleSingletonVector,100));
        System.out.println("SyncSingleton:"+judge(syncSingletonVector,100));
        System.out.println("ReferenceSingleton:"+judge(referenceSingletonVector,100));

    }

    private static <T extends List> boolean judge(T list,int cnt)
    {
        Object obj=list.get(0);
        for (Object element:list)
        {
            if(element==obj)
                cnt--;
        }
        return cnt == 0;
    }
}
