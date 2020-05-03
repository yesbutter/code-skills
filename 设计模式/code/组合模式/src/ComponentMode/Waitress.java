package ComponentMode;

import ComponentMode.Iterator.Iterator;
import ComponentMode.Menu.MenuComponent;

public class Waitress {
    MenuComponent allMenus;

    public Waitress(MenuComponent allMenus) {
        this.allMenus = allMenus;
    }

    public void printMenu() {
        allMenus.print();
    }


    public void printVegetarianMenu() {
        Iterator iterator = allMenus.createIterator();
        System.out.println("Vegetarian menu");
        while (iterator.hasNext()) {
            MenuComponent menuComponent = iterator.next();
            try {
                if (menuComponent.isVegetarian()) {
                    menuComponent.print();
                }
            } catch (UnsupportedOperationException e) {
            }
        }
    }

}
