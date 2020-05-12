package LinkedList;

import java.util.LinkedList;

/**
 * 由linkedNode 组成的连续空间，易于插入和删除，只需要改变指向关系
 * 不易于查询和定位，需要每次都遍历整个数组
 * 双向链表+头指针 尾指针
 */
public class MyLinkedList<T> {

    private int size = 0;

    class MyLinkedNode<T> {
        T data;
        MyLinkedNode<T> prev;
        MyLinkedNode<T> next;

        public MyLinkedNode() {
        }

        public MyLinkedNode(T data, MyLinkedNode<T> prev, MyLinkedNode<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    // 头尾指针， 不需要序列化
    transient MyLinkedNode<T> first;
    transient MyLinkedNode<T> last;

    public MyLinkedList() {
    }


    //下标从0开始
    public T get(int index) {
        return getMyLinkedNode(index).data;
    }


    MyLinkedNode<T> getMyLinkedNode(int position) {
        if (position >= size)
            throw new IndexOutOfBoundsException("Index: " + position + ", Size: " + size);
        MyLinkedNode<T> node;
        int count = 0;
        if (position < (size << 1)) {
            node = first;
            while (count++ != position) {
                node = node.next;
            }
        } else {
            node = last;
            while (count++ != position) {
                node = node.prev;
            }
        }
        return node;
    }

    //默认在尾部添加
    public void add(T t) {
        linkLast(t);
    }

    public void add(T t, int position) {
        final MyLinkedNode<T> l = getMyLinkedNode(position);
        linkBefore(t, l);
    }

    public void linkBefore(T t, MyLinkedNode<T> succ) {
        final MyLinkedNode<T> pred = succ.prev;
        MyLinkedNode<T> myLinkedNode = new MyLinkedNode<T>(t, pred, succ);
        succ.prev = myLinkedNode;
        if (pred == null) {
            first = myLinkedNode;
        } else {
            pred.next = myLinkedNode;
        }
        size++;
    }

    public void linkLast(T t) {
        final MyLinkedNode<T> l = last;
        MyLinkedNode<T> myLinkedNode = new MyLinkedNode<T>(t, l, null);
        //修改尾指针位置
        last = myLinkedNode;
        //如果暂存之前尾指针为空表示，还未存在元素
        if (l == null) {
            first = myLinkedNode;
        } else {
            //不为空 建立指向关系
            l.next = myLinkedNode;
        }
        size++;
    }

    //头插法
    public void headAdd(T t) {
        final MyLinkedNode<T> h = first;
        MyLinkedNode<T> myLinkedNode = new MyLinkedNode<T>(t, null, h);
        first = myLinkedNode;
        //如果头结点为空表示还没有头结点
        if (h == null) {
            last = myLinkedNode;
        } else {
            h.prev = myLinkedNode;
        }
        size++;
    }

    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        {

            linkedList.add(10);
            linkedList.add(11);
            linkedList.add(0, 18);
            linkedList.add(15);
            linkedList.add(16);
            linkedList.add(3, 18);
            linkedList.add(12);
            linkedList.add(13);
        }
        for (Integer i : linkedList) {
            System.out.print(i+" ");
        }

        System.out.println();
        System.out.println("-----------------");
        MyLinkedList<Integer> myLinkedList = new MyLinkedList<Integer>();
        {

            myLinkedList.add(10);
            myLinkedList.add(11);
            myLinkedList.add(18, 0);
            myLinkedList.add(15);
            myLinkedList.add(16);
            myLinkedList.add(18, 3);
            myLinkedList.add(12);
            myLinkedList.add(13);
        }

        int size = myLinkedList.size;
        for (int i = 0; i < size; i++) {
            System.out.print(myLinkedList.get(i)+" ");
        }
    }
}
