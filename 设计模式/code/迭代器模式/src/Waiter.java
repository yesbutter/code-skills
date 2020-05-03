import Iterator.Iterator;

import Menu.DinerMenu;
import Menu.MenuItem;
import Menu.PancakeHouseMenu;

public class Waiter {

    private PancakeHouseMenu pancakeHouseMenu;
    private DinerMenu dinerMenu;

    public Waiter(PancakeHouseMenu pancakeHouseMenu, DinerMenu dinerMenu) {
        this.pancakeHouseMenu = pancakeHouseMenu;
        this.dinerMenu = dinerMenu;
    }

    public void printMenu() {
        Iterator iterator = dinerMenu.getDinnerIterator();
        Iterator iterator1 = pancakeHouseMenu.getPancakeIterator();

        printMenu("dinerMenu",iterator);
        printMenu("pancakeHouseMenu",iterator1);
    }

    private void printMenu(String name,Iterator iterator)
    {
        System.out.println(name+":");
        while (iterator.hasNext())
        {
            MenuItem menuItem=iterator.next();
            System.out.println(menuItem);
        }
        System.out.println("---------------");
    }
}
