package Iterator;


public class ArrayIterator<T> implements Iterator {

    T[] arrays;
    int position = 0;

    public ArrayIterator(T[] arrays) {
        this.arrays = arrays;
    }

    @Override
    public T next() {
        return arrays[position++];
    }

    @Override
    public boolean hasNext() {
        return position < arrays.length && arrays[position] != null;
    }

}
