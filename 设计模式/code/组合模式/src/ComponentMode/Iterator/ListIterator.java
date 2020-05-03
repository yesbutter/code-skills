package ComponentMode.Iterator;

import java.util.List;

public class ListIterator<T> implements Iterator {
    private List<T> list;
    private int position = 0;

    public ListIterator(List<T> list) {
        this.list = list;
    }

    @Override
    public T next() {
        T t=null;
        if (hasNext())
            t=list.get(position++);
        return t;
    }

    @Override
    public boolean hasNext() {
        return position < list.size() && list.get(position) != null;
    }
}
