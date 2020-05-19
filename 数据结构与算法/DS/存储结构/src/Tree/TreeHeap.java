package Tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * @description: 树堆
 * @author: lhg
 * @email: 1296821114@qq.com
 * @create: 2020-05-19 10:15
 */
public class TreeHeap<E extends Comparable<E>> {

    private TreeHeapNode<E> mRoot;
    private Random random;

    private static final int DEFAULT_RD = 1 << 10;

    private int size = 0;


    public TreeHeap() {
        random = new Random();
    }

    static class TreeHeapNode<E extends Comparable<E>> {
        E data;
        TreeHeapNode<E> rChild, lChild;
        //在Java中，以我现在的见解，无论如何都需要建立和父节点的联系
        int priority; //优先级，用来和实现堆性质

        //TODO 可以自定义内容优先级，不过需要重写random的随机函数
        public TreeHeapNode(E data, int priority) {
            this.data = data;
            this.priority = priority;
        }

        @Override
        public String toString() {
            return "data:" + data +
                    " priority:" + priority +
                    " lChild" + (lChild == null ? "" : lChild.data) +
                    " rChild" + (rChild == null ? "" : rChild.data);
        }
    }

    public void insert(E data) {
        //首先找插入位置，其次通过旋转满足堆的性质
        insert(mRoot, data);
    }

    //插入支持相同的元素,应该是递归的会好点，返回根元素
    private TreeHeapNode<E> insert(TreeHeapNode<E> node, E data) {
        if (node == null) {
            size++;
            return new TreeHeapNode<>(data, random.nextInt(DEFAULT_RD));
        }
        if (data.compareTo(node.data) < 0) {
            node.lChild = insert(node.lChild, data);
        } else if (data.compareTo(node.data) >= 0) {
            node.rChild = insert(node.rChild, data);
        }
        //reBalance,插入之后判断父节点的和当前结点的关系。
        if (node.lChild != null && node.priority > node.lChild.priority) {
            return rightRotate(node);
        } else if (node.rChild != null && node.priority < node.rChild.priority) {
            return leftRotate(node);
        }
        return node;
    }


    /**
     * x
     *
     * @param x t1     y
     * @return t2   t3
     */
    private TreeHeapNode<E> leftRotate(TreeHeapNode<E> x) {
        TreeHeapNode<E> y = x.rChild;
        TreeHeapNode<E> tmp = y.lChild;
        y.lChild = x;
        x.rChild = tmp;
        return y;
    }


    private TreeHeapNode<E> rightRotate(TreeHeapNode<E> x) {
        TreeHeapNode<E> y = x.lChild;
        TreeHeapNode<E> tmp = y.rChild;
        y.rChild = x;
        x.lChild = tmp;
        return y;
    }

    private void levelTraversal(TreeHeapNode<E> node) {
        TreeHeapNode<E> nlast = node;
        TreeHeapNode<E> last = node;
        Queue<TreeHeapNode<E>> queue = new LinkedList<>();
        queue.add(node);
        TreeHeapNode<E> tmp;
        while (!queue.isEmpty()) {
            tmp = queue.poll();
            System.out.print(tmp.data + "           ");
            if (tmp.lChild != null) {
                queue.add(tmp.lChild);
                nlast = tmp.lChild;
            }
            if (tmp.rChild != null) {
                queue.add(tmp.rChild);
                nlast = tmp.rChild;
            }
            if (tmp == last) {
                System.out.println();
                last = nlast;
            }
        }
    }

    public TreeHeapNode<E> search(E data) {
        return search(mRoot, data);
    }

    private TreeHeapNode<E> search(TreeHeapNode<E> node, E data) {
        int cmp = 0;
        while (node != null) {
            cmp = data.compareTo(node.data);
            if (cmp < 0) {
                node = node.lChild;
            } else if (cmp > 0) {
                node = node.rChild;
            } else {
                return node;
            }
        }
        return node;
    }


    //TODO 继续完善删除逻辑
    public void remove(E e) {
        TreeHeapNode<E> node = search(e);
        if (node != null) {
//            remove(node);
        }
    }

    private void remove(TreeHeapNode<E> mRoot, E e) {
    }

    public static void main(String[] args) {
        TreeHeap treeHeap = new TreeHeap();
    }
}
