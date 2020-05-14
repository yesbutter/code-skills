package Tree;

import java.util.Stack;

/**
 * 二叉树
 * 不便于查找，树过深
 */
public class MyTree<T> {
//    TreeMap;
//    HashMap

    TreeNode<T> root;

    public MyTree() {
        this.root = new TreeNode<>();
    }

    //递归先序
    public static <T> void prevOrder(TreeNode<T> treeNode) {
        //数据访问
        if (treeNode == null)
            return;
        System.out.print(treeNode.data + " ");
        prevOrder(treeNode.lChild);
        prevOrder(treeNode.rChild);
    }

    //非递归先序 模拟堆栈存储的一个过程
    public static <T> void prev(TreeNode<T> root) {
        TreeNode<T> treeNode = root;
        //TODO 自定义的stack
        Stack<TreeNode<T>> stack = new Stack<>();
        stack.push(root);
        while (treeNode != null || !stack.empty()) {
            while (treeNode != null) {
                //先序访问
                System.out.print(treeNode.data + " ");
                stack.push(treeNode.lChild);
                treeNode = treeNode.lChild;
            }
            if (!stack.empty()) {
                treeNode = stack.pop();
                //转到右子树
                if (treeNode != null) {
                    stack.push(treeNode.rChild);
                    treeNode = treeNode.rChild;
                }
            }
        }
    }


    //递归先序
    public static <T> void midOrder(TreeNode<T> treeNode) {
        //数据访问
        if (treeNode == null)
            return;
        midOrder(treeNode.lChild);
        System.out.print(treeNode.data + " ");
        midOrder(treeNode.rChild);
    }


    //非递归中序 模拟堆栈存储的一个过程
    public static <T> void mid(TreeNode<T> root) {
        TreeNode<T> treeNode = root;
        //TODO 自定义的stack
        Stack<TreeNode<T>> stack = new Stack<>();
        stack.push(root);
        while (treeNode != null || !stack.empty()) {
            while (treeNode != null) {
                stack.push(treeNode.lChild);
                treeNode = treeNode.lChild;
            }
            if (!stack.empty()) {
                treeNode = stack.pop();
                //转到右子树
                if (treeNode != null) {
                    System.out.print(treeNode.data + " ");
                    stack.push(treeNode.rChild);
                    treeNode = treeNode.rChild;
                }
            }
        }
    }
    public static void main(String[] args) {
        MyTree<Integer> myTree = new MyTree<>();
        {

            TreeNode<Integer> treeNode2 = new TreeNode<Integer>(2);
            TreeNode<Integer> treeNode3 = new TreeNode<Integer>(3);
            TreeNode<Integer> treeNode4 = new TreeNode<Integer>(4);
            TreeNode<Integer> treeNode5 = new TreeNode<Integer>(5);
            TreeNode<Integer> treeNode6 = new TreeNode<Integer>(6);
            myTree.root.data = 1;
            myTree.root.lChild = treeNode2;
            myTree.root.rChild = treeNode5;

            treeNode2.lChild = treeNode4;
            treeNode2.rChild = treeNode3;

            treeNode5.rChild = treeNode6;

        }

        MyTree.prevOrder(myTree.root);
        System.out.println();
        MyTree.prev(myTree.root);
    }
}
