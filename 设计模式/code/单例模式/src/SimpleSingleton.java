public class SimpleSingleton {
    //懒汉式单例
    private static SimpleSingleton INSTANCE=new SimpleSingleton();

    private SimpleSingleton(){};

    public static SimpleSingleton getInstance()
    {
        return INSTANCE;
    }
}
