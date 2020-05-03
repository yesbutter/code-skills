package ComponentMode.Menu;

import ComponentMode.Iterator.Iterator;

public abstract class MenuComponent {

    public void add(MenuComponent menuComponent)
    {
        throw new UnsupportedOperationException();
    }
    public void remove(MenuComponent menuComponent)
    {
        throw new UnsupportedOperationException();
    }
    public MenuComponent getChild(int position)
    {
        throw new UnsupportedOperationException();
    }

    public String getName()
    {
        throw new UnsupportedOperationException();
    }
    public String getDescription()
    {
        throw new UnsupportedOperationException();
    }
    public double getPrice()
    {
        throw new UnsupportedOperationException();
    }
    public boolean isVegetarian()
    {
        throw new UnsupportedOperationException();
    }


    public Iterator createIterator()
    {
        throw new UnsupportedOperationException();
    }

    public void print()
    {
        throw new UnsupportedOperationException();
    }
}
