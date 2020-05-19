package Tree;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @description: 伸展树。每次插入，查找的节点会被放入到根节点。并且满足二叉搜索树的性质
 * 实现相对简单，程序局部性原理，适合访问一段段连续的空间。
 * @author: lhg
 * @email: 1296821114@qq.com
 * @create: 2020-05-18 13:42
 */
public class SplayTree<E extends Comparable<E>> {

    //    PriorityQueue
    private TreeNode<E> mRoot;
    private int size = 0;

    public void insert(E data) {
        if (data == null)
            throw new NullPointerException();
        mRoot = insert(mRoot, data);
    }

    /**
     * 每次插入之后返回根节点。
     * 插入后通过旋转操作来将插入节结点放到根结点
     *
     * @param node
     * @param data
     * @return
     */
    //TODO 相同结点的考虑
    private TreeNode<E> insert(TreeNode<E> node, E data) {
        int cmp = 0;
        TreeNode<E> parent = node;
        while (node != null) {
            parent = node;
            cmp = data.compareTo(node.data);
            if (cmp < 0) {
                node = node.lChild;
            } else {
                node = node.rChild;
            }
        }

        TreeNode<E> newNode = new TreeNode<>(data);
        newNode.parent = parent;
        if (parent != null) {
            cmp = data.compareTo(parent.data);
            if (cmp < 0) {
                parent.lChild = newNode;
            } else {
                parent.rChild = newNode;
            }
        }
        size++;
        upNode2Root(newNode);
        return newNode;
    }

    public TreeNode<E> search(E data) {
        TreeNode<E> node = search(mRoot, data);
        if (node == null)
            return null;
        //将node递归上移，进行平衡。
        upNode2Root(node);
        return mRoot;
    }

    private void upNode2Root(TreeNode<E> node) {
        TreeNode<E> grandFather;
        while (node.parent != null) {
            if (node.parent.lChild == node) {
                //当前结点是左孩子,右旋父结点
                rightRotate(node.parent);
            } else {
                //当前结点是右孩子，左旋父结点
                leftRotate(node.parent);
            }
        }
    }

    /**
     * 查找data元素所在的位置，并将这个位置通过旋转上升到根结点
     *
     * @param node 查找结点
     * @param data 查找数据
     * @return 根节点
     */
    private TreeNode<E> search(TreeNode<E> node, E data) {
        if (node == null)
            return null;
        int cmp = data.compareTo(node.data);
        if (cmp < 0) {
            return search(node.lChild, data);
        } else if (cmp > 0) {
            return search(node.rChild, data);
        } else {
            return node;
        }
    }

    /**
     * 移除元素
     *
     * @param data
     */
    public void remove(E data) {
        if (mRoot == null)
            return;
        TreeNode<E> node = search(mRoot, data);
        if (node == null)
            return;
        upNode2Root(node);
        //找后继结点
        remove(node);
        --size;
    }

    /**
     * 删除在结点中的第一次查找到的这个元素
     * 先把这个元素放到根结点，然后再进行删除
     *
     * @param node
     * @param
     */
    private void remove(TreeNode<E> node) {
        if (node.lChild != null && node.rChild != null) {
            TreeNode<E> tmp = successor(node);
            node.data = tmp.data;
            node = tmp;
        }
        TreeNode<E> replace;
        if (node.lChild != null) {
            replace = node.lChild;
        } else {
            replace = node.rChild;
        }
        if (replace != null) {
            if (node.parent == null) {
                mRoot = replace;
            } else if (node == node.parent.lChild) {
                node.parent.lChild = replace;
            } else {
                node.parent.rChild = replace;
            }
            node.lChild = node.rChild = node.parent = null;
        } else if (node.parent == null) {
            mRoot = null;
        } else {
            //删除结点没有子节点,而且存在夫结点
            if (node.parent.lChild == node) {
                node.parent.lChild = null;
            } else {
                node.parent.rChild = null;
            }
            node.parent = null;
        }
    }

    private TreeNode<E> successor(TreeNode<E> node) {
        if (node == null) return null;
        TreeNode<E> replace;
        if (node.rChild != null) {
            replace = node.rChild;
            while (replace.lChild != null) {
                replace = replace.lChild;
            }
        } else {
            replace = node.lChild;
            while (replace.rChild != null) {
                replace = replace.rChild;
            }
        }
        return replace;
    }


    public void levelTraversal() {
        levelTraversal(mRoot);
    }


    public void mid() {
        mid(mRoot);
        System.out.println();
    }

    private void mid(TreeNode<E> node) {
        if (node == null)
            return;
        mid(node.lChild);
        System.out.print(node.data + " ");
        mid(node.rChild);
    }


    private void levelTraversal(TreeNode<E> node) {
        TreeNode<E> nlast = node;
        TreeNode<E> last = node;
        Queue<TreeNode<E>> queue = new LinkedList<>();
        queue.add(node);
        TreeNode<E> tmp;
        while (!queue.isEmpty()) {
            tmp = queue.poll();
            System.out.print(tmp.data + "    ");
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

    /**
     * y结点想上位
     * 类似与红黑树的左旋
     *
     * @param x
     * @return
     */
    private void leftRotate(TreeNode<E> x) {
        if (x != null) {
            TreeNode<E> y = x.rChild;

            //建立x和t2的关系
            x.rChild = y.lChild;
            if (y.lChild != null) {
                y.lChild.parent = x;
            }

            //修改y和x父结点指针关系
            y.parent = x.parent;
            if (x.parent == null) {
                //说明当前结点是根结点
                mRoot = y;
            } else if (x.parent.lChild == x) {
                x.parent.lChild = y;
            } else {
                x.parent.rChild = y;
            }

            //建立x,y的关系
            y.lChild = x;
            x.parent = y;
        }
    }

    /**
     * y想上位            x
     * y     t3
     *
     * @param x t1   t2
     */
    private void rightRotate(TreeNode<E> x) {
        if (x != null) {
            TreeNode<E> y = x.lChild;

            x.lChild = y.rChild;
            if (y.rChild != null) {
                y.rChild.parent = x;
            }

            y.parent = x.parent;
            if (x.parent == null) {
                //当前结点是根结点
                mRoot = y;
            } else if (x.parent.lChild == x) {
                x.parent.lChild = y;
            } else {
                x.parent.rChild = y;
            }

            //建立xy关系
            y.rChild = x;
            x.parent = y;
        }
    }

    public static void main(String[] args) {
        SplayTree<Integer> tree = new SplayTree<>();
        tree.insert(5);
        tree.mid();
        tree.insert(6);
        tree.mid();
        tree.insert(8);
        tree.mid();
        tree.insert(9);
        tree.mid();
        tree.insert(10);
        tree.mid();
        tree.insert(15);
        tree.mid();
        tree.insert(7);
        tree.insert(12);
        tree.insert(14);
        tree.mid();

        tree.remove(8);
        tree.mid();
    }
}
