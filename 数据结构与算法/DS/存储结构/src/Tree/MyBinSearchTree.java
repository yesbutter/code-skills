package Tree;

/**
 * 遵循，左结点的值比当前结点的值小 右结点的值大于等于当前结点的值
 * 优点是查找速度比较块，但是缺点是 分布不均衡
 */
public class MyBinSearchTree<E extends Comparable<E>> {

    private TreeNode<E> root;
    private int size = 0;

    public MyBinSearchTree() {
        root = null;
    }

    public int getHeight() {
        return getHeight(root);
    }

    public TreeNode<E> getRoot() {
        return root;
    }

    public int getHeight(TreeNode<E> node) {
        if (node == null)
            return 0;
        int left = getHeight(node.lChild);
        int right = getHeight(node.rChild);
        return Math.max(left, right) + 1;
    }

    public MyBinSearchTree(E value) {
        root = new TreeNode<>(value);
    }

    public void add(E data) {
        if (root == null) {
            root = new TreeNode<>(data);
            size++;
            return;
        }
        add(root, data);
    }

    public static <E extends Comparable<E>> boolean isBST(TreeNode<E> root) {
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


    public TreeNode<E> search(E data) {
        return search(root, data);
    }

    private TreeNode<E> search(TreeNode<E> node, E data) {
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

    //递归实现
    @Deprecated
    public void addNodeOrder(TreeNode<E> node, E data) {
        TreeNode<E> next;
        if (data.compareTo(node.data) >= 0) {
            next = node.rChild;
            if (next == null) {
                node.rChild = new TreeNode<E>(data);
                return;
            }
        } else {
            next = node.lChild;
            if (next == null) {
                node.lChild = new TreeNode<E>(data);
                return;
            }
        }
        add(next, data);
    }

    public void add(TreeNode<E> node, E data) {
        TreeNode<E> treeNode = node;
        TreeNode<E> position = null;
        while (treeNode != null) {
            position = treeNode;
            if (data.compareTo(treeNode.data) > 0) {
                treeNode = treeNode.rChild;
            } else {
                treeNode = treeNode.lChild;
            }
        }
        TreeNode<E> newNode =new TreeNode<>(data);
        newNode.parent = position;
        if(position == null)
            root = newNode;
        else {
            if(data.compareTo(position.data) > 0){
                position.rChild = newNode;
            }else {
                position.lChild = newNode;
            }
        }
    }

    public void updateNodeValue(TreeNode<E> node, TreeNode<E> value) {
        if (node.parent.lChild != null && node.parent.lChild == node)
            node.parent.lChild = value;
        else
            node.parent.rChild = value;
    }


    public TreeNode<E> remove(TreeNode<E> node) {

        if (node.lChild != null && node.rChild != null) {
            //左右节点都在，找右子树中最小的元素交换值。
            TreeNode<E> minTreeNode = node.rChild;
            while (minTreeNode.lChild != null) {
                minTreeNode = minTreeNode.lChild;
            }
            E tmp = minTreeNode.data;
            minTreeNode.data = node.data;
            node.data = tmp;
            node = minTreeNode;
        }

        TreeNode<E> child;
        if (node.lChild != null)
            child = node.lChild;
        else
            child = node.rChild;
        if (child != null) {
            //节点交换
            child.parent = node.parent;
        }
        //如果当前节点没有父节点，表示是跟结点
        if (node.parent == null) {
            root = child;
        } else if (node == node.parent.lChild) {
            //当前结点是父节点的左结点
            node.parent.lChild = child;
        } else {
            node.parent.rChild = child;
        }
        return node;
    }

    public TreeNode<E> remove(E data) {
        //首先要查找到这个数字,只会删除出现的第一次
        TreeNode<E> treeNode = search(data);
        if (treeNode == null)
            return null;
        return remove(treeNode);

    }

    public static void main(String[] args) {
        MyBinSearchTree<Integer> myBinSearchTree = new MyBinSearchTree<>(10);

        {

            myBinSearchTree.add(5);
            myBinSearchTree.add(6);
            myBinSearchTree.add(12);
            myBinSearchTree.add(11);
            myBinSearchTree.add(13);
            myBinSearchTree.add(20);
            myBinSearchTree.add(88);
        }

        MyTree.mid(myBinSearchTree.root);
        System.out.println();
        myBinSearchTree.remove(12);
        MyTree.mid(myBinSearchTree.root);

        System.out.println();
        System.out.println(MyBinSearchTree.isBST(myBinSearchTree.root));
    }
}
