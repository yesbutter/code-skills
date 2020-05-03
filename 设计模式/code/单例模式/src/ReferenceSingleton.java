import java.lang.ref.Reference;
import java.util.concurrent.atomic.AtomicReference;

public class ReferenceSingleton {

    //RxJava 中的singleton
    private static final AtomicReference<ReferenceSingleton> INSTANCE = new AtomicReference<>();

    private ReferenceSingleton(){}

    public static ReferenceSingleton getInstance()
    {
        while (true)
        {
            ReferenceSingleton current=INSTANCE.get();
            if(current!=null)
            {
                return current;
            }
            current=new ReferenceSingleton();
            if(INSTANCE.compareAndSet(null,current))
            {
                return current;
            }
        }
    }

}
