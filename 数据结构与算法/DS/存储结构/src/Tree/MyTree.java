package Tree;

import java.util.Stack;

/**
 * 二叉树
 * 不便于查找，树过深
 */
public class MyTree<T> {
//    TreeMap;
//    HashMap

    MyTreeNode<T> root;

    public MyTree() {
        this.root = new MyTreeNode<>();
    }

    //递归先序
    public static <T> void prevOrder(MyTreeNode<T> myTreeNode) {
        //数据访问
        if (myTreeNode == null)
            return;
        System.out.print(myTreeNode.data + " ");
        prevOrder(myTreeNode.lChild);
        prevOrder(myTreeNode.rChild);
    }

    //非递归先序 模拟堆栈存储的一个过程
    public static <T> void prev(MyTreeNode<T> root) {
        MyTreeNode<T> myTreeNode = root;
        //TODO 自定义的stack
        Stack<MyTreeNode<T>> stack = new Stack<>();
        stack.push(root);
        while (myTreeNode != null || !stack.empty()) {
            while (myTreeNode != null) {
                //先序访问
                System.out.print(myTreeNode.data + " ");
                stack.push(myTreeNode.lChild);
                myTreeNode = myTreeNode.lChild;
            }
            if (!stack.empty()) {
                myTreeNode = stack.pop();
                //转到右子树
                if (myTreeNode != null) {
                    stack.push(myTreeNode.rChild);
                    myTreeNode = myTreeNode.rChild;
                }
            }
        }
    }


    //递归先序
    public static <T> void midOrder(MyTreeNode<T> myTreeNode) {
        //数据访问
        if (myTreeNode == null)
            return;
        midOrder(myTreeNode.lChild);
        System.out.print(myTreeNode.data + " ");
        midOrder(myTreeNode.rChild);
    }


    //非递归中序 模拟堆栈存储的一个过程
    public static <T> void mid(MyTreeNode<T> root) {
        MyTreeNode<T> myTreeNode = root;
        //TODO 自定义的stack
        Stack<MyTreeNode<T>> stack = new Stack<>();
        stack.push(root);
        while (myTreeNode != null || !stack.empty()) {
            while (myTreeNode != null) {
                stack.push(myTreeNode.lChild);
                myTreeNode = myTreeNode.lChild;
            }
            if (!stack.empty()) {
                myTreeNode = stack.pop();
                //转到右子树
                if (myTreeNode != null) {
                    System.out.print(myTreeNode.data + " ");
                    stack.push(myTreeNode.rChild);
                    myTreeNode = myTreeNode.rChild;
                }
            }
        }
    }
    public static void main(String[] args) {
        MyTree<Integer> myTree = new MyTree<>();
        {

            MyTreeNode<Integer> myTreeNode2 = new MyTreeNode<Integer>(2);
            MyTreeNode<Integer> myTreeNode3 = new MyTreeNode<Integer>(3);
            MyTreeNode<Integer> myTreeNode4 = new MyTreeNode<Integer>(4);
            MyTreeNode<Integer> myTreeNode5 = new MyTreeNode<Integer>(5);
            MyTreeNode<Integer> myTreeNode6 = new MyTreeNode<Integer>(6);
            myTree.root.data = 1;
            myTree.root.lChild = myTreeNode2;
            myTree.root.rChild = myTreeNode5;

            myTreeNode2.lChild = myTreeNode4;
            myTreeNode2.rChild = myTreeNode3;

            myTreeNode5.rChild = myTreeNode6;

        }

        MyTree.prevOrder(myTree.root);
        System.out.println();
        MyTree.prev(myTree.root);
    }
}
