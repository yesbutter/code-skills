package Iterator;

import Menu.MenuItem;

import java.util.List;

public class ListIterator<T> implements Iterator {
    private List<T> menuList;
    private int position = 0;

    public ListIterator(List<T> menuList) {
        this.menuList = menuList;
    }


    @Override
    public T next() {
        return menuList.get(position++);
    }

    @Override
    public boolean hasNext() {
        return position < menuList.size() && menuList.get(position) != null;
    }
}
