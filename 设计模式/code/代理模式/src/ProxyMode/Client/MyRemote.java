package ProxyMode.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyRemote extends Remote {

    //要保证返回值是可序列化或者原语类型
    public String sayHello() throws RemoteException;
}