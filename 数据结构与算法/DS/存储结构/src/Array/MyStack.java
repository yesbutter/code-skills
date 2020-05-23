package Array;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * @description: 栈
 * @author: lhg
 * @email: 1296821114@qq.com
 * @create: 2020-05-23 12:52
 */
public class MyStack<E> extends MyArray<E> {


    public MyStack() {
        super();
    }

    public synchronized E push(E data) {
        add(data);
        return data;
    }

    public synchronized E pop() {
        E obj = peek();
        data[--position] = null;
        return obj;
    }

    @SuppressWarnings("unchecked")
    public synchronized E peek() {
        int len = position;
        if (len == 0)
            throw new EmptyStackException();
        return (E) data[len - 1];
    }

    public static void main(String[] args) {
        MyStack<Integer> s = new MyStack<>();
        s.push(5);
        System.out.println(s.peek());
        s.push(6);
        System.out.println(s.peek());
        System.out.println(s.pop());
        System.out.println(s.pop());
    }
}
