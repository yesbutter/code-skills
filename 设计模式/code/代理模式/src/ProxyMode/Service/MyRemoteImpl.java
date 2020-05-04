package ProxyMode.Service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * 服务端只需要运行程序即可。
 * MyRemote的方法内容，需要是可以序列化的
 */
public class MyRemoteImpl  extends UnicastRemoteObject implements MyRemote {
    public MyRemoteImpl() throws RemoteException {
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Server says,Hey";
    }

	 public static void main(String[] args) {
		MyRemote service= null;
		try {
		    service = new MyRemoteImpl();
		    //使用创建就不需要再使用 rmiregistry
			LocateRegistry.createRegistry(1099);
		    Naming.rebind("rmi://localhost/RemoteHello",service);
		} catch (RemoteException e) {
		    e.printStackTrace();
		} catch (MalformedURLException e) {
		    e.printStackTrace();
		}

	//        System.out.println(gumballMachine);
	    }
}
