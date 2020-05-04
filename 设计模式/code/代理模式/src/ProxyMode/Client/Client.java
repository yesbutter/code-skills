package ProxyMode.Client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * 客户端运行程序
 * 客户端可以使用代理对象获取服务端提供的方法调用
 */
public class Client {

    public static void main(String[] args) {

        MyRemote service=null;
        try {
            //在linux service ,windows client时需要注意修改主机名，否则会导致无法调用方法
            service =(MyRemote)Naming.lookup("rmi://localhost:1099/RemoteHello");
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
//        while (true)
        {
            try {
                System.out.println(service.sayHello());
                System.out.println(service);
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
