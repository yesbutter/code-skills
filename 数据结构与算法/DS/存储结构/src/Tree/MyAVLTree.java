package Tree;

/**
 * AVL 树具有良好的平衡性
 * 1.平衡计算
 * 2.不平衡的旋转 4种 LL RR LR RL
 * <p>
 * 概念:节点后继
 *
 * @param <E>
 */
public class MyAVLTree<E extends Comparable<E>> {

    private AVLTreeNode<E> root;
    private int size;

    class AVLTreeNode<E extends Comparable<E>> {
        E data;
        protected AVLTreeNode<E> lChild, rChild;
        protected int height;

        public AVLTreeNode(E data) {
            this.data = data;
            this.lChild = this.rChild = null;
            this.height = 1;
        }
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(AVLTreeNode<E> node) {
        if (node == null)
            return 0;
        return node.height;
    }

    public MyAVLTree() {
        this.root = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    private int getBalanceFactor(AVLTreeNode<E> node) {
        if (node == null)
            return 0;
        return getHeight(node.lChild) - getHeight(node.rChild);
    }

    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(AVLTreeNode<E> node) {
        if (node == null)
            return true;
        int balanceFactory = Math.abs(getBalanceFactor(node));
        if (balanceFactory > 1)
            return false;
        return isBalanced(node.lChild) && isBalanced(node.rChild);
    }


    public void add(E data) {
        if (root == null) {
            root = new AVLTreeNode<>(data);
            size++;
            return;
        }
        root = add(root, data);
    }

    /**
     * 增加会修改元素的顺序
     *
     * @param node
     * @param data
     * @return
     */
    private AVLTreeNode<E> add(AVLTreeNode<E> node, E data) {
        if (node == null) {
            size++;
            return new AVLTreeNode<>(data);
        }
        if (data.compareTo(node.data) < 0) {
            node.lChild = add(node.lChild, data);
        } else if (data.compareTo(node.data) > 0) {
            node.rChild = add(node.rChild, data);
        }
        updateHeight(node);
        int balanceFactor = getBalanceFactor(node);
        if (balanceFactor > 1 && getBalanceFactor(node.lChild) > 0) {
            //LL
            return rightRotate(node);
        } else if (balanceFactor < -1 && getBalanceFactor(node.rChild) < 0) {
            //RR
            return leftRotate(node);
        } else if (balanceFactor > 1 && getBalanceFactor(node.rChild) < 0) {
            //LR
            node.lChild = leftRotate(node.lChild);
            return rightRotate(node);
        } else if (balanceFactor < -1 && getBalanceFactor(node.lChild) > 0) {
            //RL
            node.rChild = rightRotate(node.rChild);
            return leftRotate(node);
        }
        return node;
    }


    /**
     * LL 右旋 返回根节点 x.
     * y                                  x                                      x
     * x       t4   (x.rChild = y)         z       y      (y.lChild = t3)         z        y
     * z     t3         --------------->    t1   t2       t4  -------------->      t1   t2  t3  t4
     * t1  t2
     *
     * @param y
     * @return
     */
    private AVLTreeNode<E> rightRotate(AVLTreeNode<E> y) {
        AVLTreeNode<E> x = y.lChild;
        AVLTreeNode<E> tmp3 = x.rChild;
        x.rChild = y;
        y.lChild = tmp3;
        //更新高度
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private AVLTreeNode<E> leftRotate(AVLTreeNode<E> y) {
        AVLTreeNode<E> x = y.rChild;
        AVLTreeNode<E> tmp3 = x.lChild;
        x.lChild = y;
        y.rChild = tmp3;

        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private void updateHeight(AVLTreeNode<E> node) {
        node.height = Math.max(getHeight(node.lChild), getHeight(node.rChild)) + 1;
    }


    public AVLTreeNode<E> search(E data) {
        return search(root, data);
    }

    private AVLTreeNode<E> search(AVLTreeNode<E> node, E data) {
        while (node != null) {
            if (data.compareTo(node.data) > 0) {
                node = node.rChild;
            } else if (data.compareTo(node.data) < 0) {
                node = node.lChild;
            } else {
                return node;
            }
        }
        return null;
    }


    public void remove(E data) {
        if (data == null)
            throw new NullPointerException();
        root = remove(root, data);
    }

    //返回当前根节点
    private AVLTreeNode<E> remove(AVLTreeNode<E> node, E data) {
        if (node == null) {
            return null;
        }
        if (data.compareTo(node.data) > 0) {
            node.rChild = remove(node.rChild, data);
        } else if (data.compareTo(node.data) < 0) {
            node.lChild = remove(node.lChild, data);
        } else {
            size--;
            if (node.lChild == null || node.rChild == null) {
                return node.lChild == null ? node.rChild : node.lChild;
            }
            E successorKey = successorOf(node).data;
            node = remove(node, successorKey);
            node.data = successorKey;
        }
        updateHeight(node);
        int balanceFactor = getBalanceFactor(node);
        if (balanceFactor > 1 || balanceFactor < -1)
            node = balance(node);
        return node;
    }

	//后继节点
    private AVLTreeNode<E> successorOf(AVLTreeNode<E> node) {
        AVLTreeNode<E> replaceTreeNode = null;
        //左右子树都不为空，寻找左右子树高度最高的。取左子树的最大值，右子树的最小值，替换
        if (node.rChild.height <= node.lChild.height) {
            replaceTreeNode = node.rChild;
            while (replaceTreeNode.lChild != null) {
                replaceTreeNode = replaceTreeNode.lChild;
            }
        } else {
            replaceTreeNode = node.lChild;
            while (replaceTreeNode.rChild != null) {
                replaceTreeNode = replaceTreeNode.rChild;
            }
        }
        return replaceTreeNode;
    }

	// 删除时调整平衡
    private AVLTreeNode<E> balance(AVLTreeNode<E> node) {
        int balanceFactor = getBalanceFactor(node);
        if (balanceFactor > 1 && getBalanceFactor(node.lChild) >= 0) {
            node = leftRotate(node);
        } else if (balanceFactor < -1 && getBalanceFactor(node.rChild) <= 0) {
            node = rightRotate(node);
        }
        return node;
    }

    public void midOrder() {
        midOrder(root);
    }

    //递归先序
    public void midOrder(AVLTreeNode<E> myTreeNode) {
        //数据访问
        if (myTreeNode == null)
            return;
        midOrder(myTreeNode.lChild);
        System.out.print(myTreeNode.data + " ");
        midOrder(myTreeNode.rChild);
    }

    public static void main(String[] args) {
        int[] array = new int[]{5, 6, 12, 11, 13, 20, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 98, 100, 101};
        MyAVLTree<Integer> myAVLTree = new MyAVLTree<Integer>();
        for (int data : array)
            myAVLTree.add(data);

        myAVLTree.midOrder();
        System.out.println();
        myAVLTree.remove(13);
        myAVLTree.midOrder();
        System.out.println();
        System.out.println(myAVLTree.getHeight());


//        MyBinSearchTree<Integer> myBinSearchTree = new MyBinSearchTree<Integer>();
//        for (int data : array)
//            myBinSearchTree.add(data);
//        System.out.println(myBinSearchTree.getHeight());
//        System.out.println(myBinSearchTree.getRoot().data);
    }
}
