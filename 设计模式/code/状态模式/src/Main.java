import Machine.GumballMachine;

public class Main {

    public static void main(String[] args) {
        /*
          对扩展开发 对修改关闭
         */

        GumballMachine gumballMachine=new GumballMachine(5);
        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();
//        System.out.println(gumballMachine);

        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();

        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();

        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();

        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();
//        System.out.println(gumballMachine);
    }
}
