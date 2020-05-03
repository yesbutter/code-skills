package Menu;


import Iterator.Iterator;
import Iterator.ArrayIterator;

public class DinerMenu {

    static final int MAX_ITEMS = 6;
    int numberOfItems = 0;
    MenuItem[] menus;

    public DinerMenu() {
        menus = new MenuItem[MAX_ITEMS];
        addItem(new MenuItem("红烧茄子"));
        addItem(new MenuItem("麻辣香锅"));
        addItem(new MenuItem("煎饼果子"));
        addItem(new MenuItem("羊杂汤"));
    }

    public void addItem(MenuItem menuItem)
    {
        if(numberOfItems>=MAX_ITEMS)
        {
            System.out.println("Sorry ,menu is full!");
        }
        else
        {
            menus[numberOfItems++]= menuItem;
        }
    }

    public Iterator getDinnerIterator()
    {
        return new ArrayIterator<MenuItem>(menus);
    }
}
