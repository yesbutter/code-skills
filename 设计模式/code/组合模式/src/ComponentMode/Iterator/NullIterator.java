package ComponentMode.Iterator;

public class NullIterator implements Iterator{

    @Override
    public <T> T next() {
        return null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }
}
