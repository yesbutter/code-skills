package Tree;

/**
 * 遵循，左结点的值比当前结点的值小 右结点的值大于等于当前结点的值
 * 优点是查找速度比较块，但是缺点是 分布不均衡
 */
public class MyBinSearchTree<E extends Comparable<E>> {

    private MyTreeNode<E> root;

    public MyBinSearchTree(E value) {
        root = new MyTreeNode<>(value);
    }

    public void addNode(E data) {
        addNode(root, data);
    }

    public static <E extends Comparable<E>> boolean isBST(MyTreeNode<E> root) {
        if (root != null) {
            if (root.lChild != null && root.lChild.data.compareTo(root.data) > 0)
                return false;
            if (root.rChild != null && root.rChild.data.compareTo(root.data) < 0)
                return false;
            isBST(root.lChild);
            isBST(root.rChild);
        }
        return true;
    }

    //递归实现
    @Deprecated
    public void addNodeOrder(MyTreeNode<E> node, E data) {
        MyTreeNode<E> next;
        if (data.compareTo(node.data) >= 0) {
            next = node.rChild;
            if (next == null) {
                node.rChild = new MyTreeNode<E>(data);
                return;
            }
        } else {
            next = node.lChild;
            if (next == null) {
                node.lChild = new MyTreeNode<E>(data);
                return;
            }
        }
        addNode(next, data);
    }

    public void addNode(MyTreeNode<E> node, E data) {
        MyTreeNode<E> myTreeNode = node;
        while (true) {
            if (data.compareTo(myTreeNode.data) >= 0) {
                if (myTreeNode.rChild == null) {
                    myTreeNode.rChild = new MyTreeNode<>(data);
                    return;
                }
                myTreeNode = myTreeNode.rChild;
            } else {
                if (myTreeNode.lChild == null) {
                    myTreeNode.lChild = new MyTreeNode<>(data);
                    return;
                }
                myTreeNode = myTreeNode.lChild;
            }
        }
    }

    public void updateNodeValue(MyTreeNode<E> prev, MyTreeNode<E> node, MyTreeNode<E> value) {
        if (prev.lChild != null && prev.lChild == node)
            prev.lChild = value;
        else
            prev.rChild = value;
    }

    public void removeNode(MyTreeNode<E> prev, MyTreeNode<E> node) {

        if (node.lChild == null && node.rChild == null) {
            updateNodeValue(prev, node, null);
        } else if (node.lChild == null) {
            updateNodeValue(prev, node, node.rChild);
        } else if (node.rChild == null) {
            updateNodeValue(prev, node, node.lChild);
        } else {
            //查找右子树的最小的元素的位置，交换
            //如果该元素有右子树，需要把右子树移到前序结点的左子树
            MyTreeNode<E> myTreeNode = node.rChild;
            MyTreeNode<E> myTreeNodePrev = node;
            while (myTreeNode.lChild != null) {
                myTreeNodePrev = myTreeNode;
                myTreeNode = myTreeNode.lChild;
            }
            if (myTreeNode.rChild != null)
                myTreeNodePrev.lChild = myTreeNode.rChild;
            if (myTreeNode == node.rChild) {
                node.rChild = null;
                myTreeNodePrev.rChild = null;
            } else {
                myTreeNodePrev.lChild = null;
            }
            updateNodeValue(prev, node, myTreeNode);
            myTreeNode.lChild = node.lChild;
            myTreeNode.rChild = node.rChild;
        }
        // Help GC
        node = null;
    }

    public boolean removeNode(E data) {
        //首先要查找到这个数字,只会删除出现的第一次
        MyTreeNode<E> myTreeNode = root;
        MyTreeNode<E> prev = null;
        while (myTreeNode != null) {
            if (data.compareTo(myTreeNode.data) < 0) {
                prev = myTreeNode;
                myTreeNode = myTreeNode.lChild;
            } else if (data.compareTo(myTreeNode.data) > 0) {
                prev = myTreeNode;
                myTreeNode = myTreeNode.rChild;
            } else {
                removeNode(prev, myTreeNode);
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        MyBinSearchTree<Integer> myBinSearchTree = new MyBinSearchTree<>(10);

        {

            myBinSearchTree.addNode(5);
            myBinSearchTree.addNode(6);
            myBinSearchTree.addNode(12);
            myBinSearchTree.addNode(11);
            myBinSearchTree.addNode(13);
            myBinSearchTree.addNode(20);
            myBinSearchTree.addNode(88);
        }

        MyTree.mid(myBinSearchTree.root);
        System.out.println();
        myBinSearchTree.removeNode(12);
        MyTree.midOrder(myBinSearchTree.root);

        System.out.println();
        System.out.println(MyBinSearchTree.isBST(myBinSearchTree.root));
    }
}
