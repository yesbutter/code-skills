package Tree;

/**
 * @description: RedBlackTree
 * 1. 每个结点是黑色、或者红色
 * 2. 根节点是黑色
 * 3. 每个叶子结点是黑色
 * 4. 如果一个节点是红色的，则它的子节点必须是黑色的
 * 5. 从一个节点到该节点的子孙节点的所有路径上包含相同数目的黑色节点。
 * @author: lhg
 * @email: 1296821114@qq.com
 * @create: 2020-05-14 14:16
 */
public class RBTree<E extends Comparable<E>> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private RBTreeNode<E> mRoot;

    class RBTreeNode<E extends Comparable<E>>{
        boolean color; //颜色
        E data;
        RBTreeNode<E> lChild,rChild,parent;

        public RBTreeNode(boolean color, E data, RBTreeNode<E> lChild, RBTreeNode<E> rChild, RBTreeNode<E> parent) {
            this.color = color;
            this.data = data;
            this.lChild = lChild;
            this.rChild = rChild;
            this.parent = parent;
        }

        public E getData() {
            return data;
        }

        @Override
        public String toString() {
            return "RBTreeNode{" +
                    "color=" + (color?"Red":"BLACK")  +
                    ", data=" + data +
                    '}';
        }
    }


}
