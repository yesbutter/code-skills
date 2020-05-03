package ComponentMode.Iterator;

import ComponentMode.Menu.Menu;

import java.util.Stack;

/**
 * 维护一个栈
 */
public class CompositeIterator <T> implements Iterator{
    Stack<Iterator> stack;

    public CompositeIterator(Iterator iterator) {
        stack=new Stack<>();
        stack.push(iterator);
    }


    @Override
    public <T> T next() {
        if(hasNext())
        {
            Iterator iterator=stack.peek();
            T t=iterator.next();
            //TODO 修改判断机制
            if(t instanceof Menu)
                stack.push(((Menu) t).createIterator());
            return t;
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean hasNext() {
        if(stack.empty())
            return false;
        else
        {
            Iterator iterator=stack.peek();
            if(!iterator.hasNext())
            {
                stack.pop();
                return hasNext();
            }else {
                return true;
            }
        }
    }
}
