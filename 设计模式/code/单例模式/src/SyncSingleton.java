public class SyncSingleton {
    //double check locking
    private volatile static SyncSingleton INSTANCE;

    private SyncSingleton() {
    }


    public static SyncSingleton getInstance() {
        if (null == INSTANCE) {
            synchronized (SyncSingleton.class) {
                if (null == INSTANCE) {
                    INSTANCE = new SyncSingleton();
                }
            }
        }
        return INSTANCE;
    }
}
