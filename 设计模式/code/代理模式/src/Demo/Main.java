package Demo;

import java.net.MalformedURLException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.CountDownLatch;

public class Main {

    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        CountDownLatch countDownLatch1 = new CountDownLatch(3);

        //做了操作之后会创建守护进程，除了杀死整个进程，目前没有发现其他方法关闭。
        Registry registry = LocateRegistry.createRegistry(1099);
        new Thread(() -> {
            try {
                countDownLatch1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Client.client(countDownLatch);
            try {
                UnicastRemoteObject.unexportObject(registry, true);
            } catch (NoSuchObjectException e) {
                e.printStackTrace();
            }
            System.gc();
        }).start();


        new Thread(() -> {
            Service service1 = null;
            try {
                service1 = new Service("Beijing", "rmi://localhost/RemoteBeijing", 150);
                countDownLatch1.countDown();

                service1.service();
                countDownLatch.countDown();
            } catch (RemoteException | InterruptedException | MalformedURLException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            Service service1 = null;
            try {
                service1 = new Service("Hangzhou", "rmi://localhost/RemoteHangzhou", 250);
                countDownLatch1.countDown();

                service1.service();
                countDownLatch.countDown();
            } catch (RemoteException | MalformedURLException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            Service service1 = null;
            try {
                service1 = new Service("Zhengzhou", "rmi://localhost/RemoteZhengzhou", 350);
                countDownLatch1.countDown();

                service1.service();
                countDownLatch.countDown();
            } catch (RemoteException | MalformedURLException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
