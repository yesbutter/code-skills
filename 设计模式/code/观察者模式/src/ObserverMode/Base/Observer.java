package ObserverMode.Base;

public interface Observer {
    public<T extends Subject> void update(T subject, Object arg);
}
