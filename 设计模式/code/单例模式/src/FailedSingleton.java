public class FailedSingleton {

    private static FailedSingleton INSTANCE;

    private FailedSingleton(){}

    public static FailedSingleton getInstance()
    {
        if(INSTANCE==null)
        {
            INSTANCE=new FailedSingleton();
        }
        return INSTANCE;
    }
}
