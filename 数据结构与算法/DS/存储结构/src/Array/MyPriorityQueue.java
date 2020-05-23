package Array;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @description: 优先队列 内部使用堆存储
 * 还有一个重要的概念为分割迭代器。通过返回区间来支持并发。使用是在并发之前先分割迭代器，不同的线程用不同的迭代器
 * @author: lhg
 * @email: 1296821114@qq.com
 * @create: 2020-05-19 08:51
 */
public class MyPriorityQueue<E extends Comparable<E>> {

    private int size = 0;
    private Object[] queue;
    private Comparator<E> comparator;
    private static final int DEFAULT_SIZE = 11;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    public MyPriorityQueue() {
        this(DEFAULT_SIZE, null);
    }

    public MyPriorityQueue(int initialCapacity, Comparator<E> comparator) {
        if (initialCapacity < 1)
            throw new IllegalArgumentException();
        this.size = 0;
        queue = new Object[initialCapacity];
        this.comparator = comparator;
    }

    public boolean add(E data) {
        return offer(data);
    }

    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }


//    @SuppressWarnings("unchecked")
//    private void heapify() {
//        for (int i = (size >>> 1) - 1; i >= 0; i--)
//            siftDown(i, (E) queue[i]);
//    }

    public boolean offer(E data) {
        if (data == null)
            throw new NullPointerException();
        if (size >= queue.length) {
            grow(size + 1);
        }
        if (size == 0) {
            queue[0] = data;
        } else {
            siftUp(size, data);
        }
        ++size;
        return true;
    }


    private void grow(int minCapacity) {
        int oldCapacity = queue.length;
        int newCapacity = oldCapacity + ((oldCapacity > 64) ?
                (oldCapacity >>> 1) :
                (oldCapacity + 2));
        if (newCapacity - MAX_ARRAY_SIZE > 0) {
            newCapacity = hugeCapacity(minCapacity);
        }
        queue = Arrays.copyOf(queue, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0)
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    //不删除的获取队列极值
    @SuppressWarnings("unchecked")
    public E peek() {
        return size == 0 ? null : (E) queue[0];
    }

    //删除的获取极值
    @SuppressWarnings("unchecked")
    public E poll() {
        if (size == 0) return null;
        int s = --size;
        E result = (E) queue[0];
        E x = (E) queue[s];
        queue[s] = null;
        if (s != 0) {
            siftDown(0, x);
        }
        return result;
    }

    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1)
            return false;
        else {
            removeAt(index);
            return true;
        }
    }

    private int indexOf(Object o) {
        if (o != null) {
            for (int i = 0; i < size; i++) {
                if (o.equals(queue[i]))
                    return i;
            }
        }
        return -1;
    }

    public void clear() {
        for (int i = 0; i < size; i++)
            queue[i] = null;
        size = 0;
    }

    @SuppressWarnings("unchecked")
    private E removeAt(int index) {
        int s = --size;
        if (s == index) {//移除最后一个元素
            queue[index] = null;
        } else {
            E moved = (E) queue[s];
            queue[s] = null;
            siftDown(index, moved);
            //移除元素后，替换结点上升到当前结点，需要继续上升处理。
            if (queue[index] == moved) {
                siftUp(index, moved);
                if (queue[index] != moved) {
                    return moved;
                }
            }
        }
        return null;
    }

    private void siftUp(int index, E data) {
        if (comparator != null) {
            siftUpUsingComparator(index, data);
        } else {
            siftUpComparable(index, data);
        }
    }

    @SuppressWarnings("unchecked")
    private void siftUpComparable(int index, E data) {
        while (index > 0) {
            int parent = (index - 1) >>> 1;
            E e = (E) queue[parent];
            if (e.compareTo(data) < 0) {
                break;
            }
            queue[index] = e;
            index = parent;
        }
        queue[index] = data;
    }

    @SuppressWarnings("unchecked")
    private void siftUpUsingComparator(int index, E data) {
        while (index > 0) {
            int parent = (index - 1) >>> 1;
            E e = (E) queue[parent];
            if (comparator.compare(e, data) < 0) {
                break;
            }
            queue[index] = e;
            index = parent;
        }
        queue[index] = data;
    }

    private void siftDown(int index, E data) {
        if (comparator != null) {
            siftDownUsingComparator(index, data);
        } else {
            siftDownComparable(index, data);
        }
    }


    @SuppressWarnings("unchecked")
    private void siftDownComparable(int index, E data) {
        int half = index >>> 1;
        while (index < half) {
            int child = (index << 1) + 1;
            E e = (E) queue[child];
            int rIndex = child + 1;
            if (rIndex < size && e.compareTo((E) queue[rIndex]) > 0) {
                e = (E) queue[child = rIndex];
            }
            if (e.compareTo(data) > 0) {
                break;
            }
            queue[index] = e;
            index = child;
        }
        queue[index] = data;
    }

    @SuppressWarnings("unchecked")
    private void siftDownUsingComparator(int index, E data) {
        int half = size >>> 1;
        while (index < half) {
            int child = (index << 1) + 1;
            E e = (E) queue[child];
            int rIndex = child + 1;
            if (rIndex < size && comparator.compare(e, (E) queue[rIndex]) > 0) {
                e = (E) queue[child = rIndex];
            }
            if (comparator.compare(data, e) < 0) {
                break;
            }
            queue[index] = e;
            index = child;
        }
        queue[index] = data;
    }

    public static void main(String[] args) {
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>();
        pq.add(5);
        pq.add(4);
        pq.add(3);
        pq.add(2);
        pq.add(51);
        pq.add(41);
        pq.add(31);
        pq.add(21);
        pq.add(512);
        pq.add(421);
        pq.add(3122);
        pq.add(112);
        System.out.println(pq.peek());
        System.out.println(pq.remove(51));

    }
}
