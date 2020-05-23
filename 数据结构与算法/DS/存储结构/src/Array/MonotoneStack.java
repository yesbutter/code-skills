package Array;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EmptyStackException;

/**
 * @description: 单调栈 栈中元素保持一定单调性
 * @author: lhg
 * @email: 1296821114@qq.com
 * @create: 2020-05-23 13:09
 */
public class MonotoneStack<E extends Comparable<E>> extends MyArray<E> {


    private Comparator<E> comparable;


    public MonotoneStack() {
        super();
        this.comparable = null;
    }

    public MonotoneStack(Comparator<E> comparable) {
        super();
        this.comparable = comparable;
    }

    /**
     * @param data
     * @return
     */
    private E pushUsingComparable(E data){
        while (position != 0 && comparable.compare(data,peek()) < 0){
            pop();
        }
        add(data);
        return data;
    }


    private E pushUsingComparator(E data){
        while (position != 0 && data.compareTo(peek()) < 0){
            pop();
        }
        add(data);
        return data;
    }

    public synchronized E push(E data) {
        return comparable == null ? pushUsingComparator(data) : pushUsingComparable(data);
    }

    public synchronized E pop() {
        E obj = peek();
        data[--position] = null;
        return obj;
    }

    @SuppressWarnings("unchecked")
    public synchronized E peek() {
        int len = position;
        if(len == 0)
            throw new EmptyStackException();
        return (E) data[len - 1];
    }


    @Override
    public String toString() {
        return "MonotoneStack{" +
                "data=" + Arrays.toString(data) +
                '}';
    }

    public static void main(String[] args) {
        MonotoneStack<Integer> s = new MonotoneStack<>();
        s.push(5);
        s.push(6);
        System.out.println(s);
        s.push(9);
        System.out.println(s);
        s.push(8);
        System.out.println(s);
        s.push(7);
        System.out.println(s);
    }
}
