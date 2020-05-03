package ComponentMode.Menu;

import ComponentMode.Iterator.CompositeIterator;
import ComponentMode.Iterator.Iterator;
import ComponentMode.Iterator.ListIterator;

import java.util.ArrayList;
import java.util.List;

public class Menu extends MenuComponent{

    List<MenuComponent> menuItemList;
    String name;
    String description;

    public Menu(String name,String description)
    {
        this.name=name;
        this.description=description;
        this.menuItemList=new ArrayList<>();
    }

    public Menu(List<MenuComponent> menuItemList, String name, String description) {
        this.menuItemList = menuItemList;
        this.name = name;
        this.description = description;
    }

    public Iterator createIterator()
    {
        return new CompositeIterator<Menu>(new ListIterator<MenuComponent>(menuItemList));
    }




    @Override
    public MenuComponent getChild(int position) {
        return menuItemList.get(position);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void add(MenuComponent menuComponent) {
        menuItemList.add(menuComponent);
    }

    @Override
    public void remove(MenuComponent menuComponent) {
        menuItemList.remove(menuComponent);
    }

    @Override
    public void print() {
        System.out.println("Menu{" +
                "menuItemList=" + menuItemList +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}');
        Iterator iterator=new ListIterator<MenuComponent>(menuItemList);
        while (iterator.hasNext())
        {
            MenuComponent menuItem=iterator.next();
            menuItem.print();
        }
    }

}
