import Menu.DinerMenu;
import Menu.PancakeHouseMenu;

import javax.swing.text.View;
import java.awt.*;

public class Main {


    public static void main(String[] args) {
        PancakeHouseMenu pancakeHouseMenu=new PancakeHouseMenu();
        DinerMenu dinerMenu=new DinerMenu();
        Waiter waiter=new Waiter(pancakeHouseMenu,dinerMenu);
        waiter.printMenu();
    }
}
