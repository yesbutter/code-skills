package Tree;

public class MyTreeNode<T> {
    T data;
    MyTreeNode<T> lChild;
    MyTreeNode<T> rChild;

    public MyTreeNode() {
        this.lChild = null;
        this.rChild = null;
    }

    public MyTreeNode(T data) {
        this.data = data;
        this.lChild = null;
        this.rChild = null;
    }
}