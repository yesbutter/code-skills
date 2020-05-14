package Tree;

public class TreeNode<E> {
    E data;
    protected TreeNode<E> lChild;
    protected TreeNode<E> rChild;
    protected TreeNode<E> parent;

    public TreeNode() {
        this.lChild = null;
        this.rChild = null;
    }

    public TreeNode(E data) {
        this.data = data;
        this.lChild = null;
        this.rChild = null;
        this.parent = null;
    }
}