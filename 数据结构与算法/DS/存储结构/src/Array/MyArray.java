package Array;

import java.util.Arrays;

/**
 * 数组存储结构是一块连续的空间，容易查找和定位。
 * 但是不便于插入和删除
 */
public class MyArray<T> {

    private static final int DEFAULT_SIZE = 10;
    private Object[] data;
    private int position;

    public MyArray() {
        data = new Object[DEFAULT_SIZE];
        position = 0;
    }

    public MyArray(int size) {
        this.position = 0;
        data = new Object[size];
    }


    //TODO 添加元素需要考虑线程安全
    public void add(T t) {
        if (position + 1 >= data.length) //超出存储范围
        {
            // + 运算 优先于 >>运算是的遵循从左到右运算
            int newLength = data.length + (data.length >> 1);
            //TODO 长度超int范围
            data = Arrays.copyOf(data, newLength);
        }
        data[position++] = t;
    }


    public T get(int index) {
        if (index >= this.position) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.position);
        }
        return (T) data[index];
    }


    public static void main(String[] args) {
        MyArray<Integer> myArray = new MyArray<>(10);

        int size = 20;
        for (int i = 0; i < size; i++)
            myArray.add(i);
        for (int i = 0; i < size; i++) {
            System.out.println(myArray.get(i));
        }


    }
}
