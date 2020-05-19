package Tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @description: RedBlackTree
 * 1. 每个结点是黑色、或者红色
 * 2. 根节点是黑色
 * 3. 每个叶子结点是黑色
 * 4. 如果一个节点是红色的，则它的子节点必须是黑色的
 * 5. 从一个节点到该节点的子孙节点的所有路径上包含相同数目的黑色节点。
 * <p>
 * 看文章看源码级的理解，能够学到很多知识。
 * @author: lhg
 * @email: 1296821114@qq.com
 * @create: 2020-05-14 14:16
 */
public class RBTree<E extends Comparable<E>> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private RBTreeNode<E> mRoot;
    private int size = 0;

    static class RBTreeNode<E extends Comparable<E>> {
        boolean color; //颜色
        E data;
        RBTreeNode<E> lChild, rChild, parent;

        //为什么需要parent指针，应该是为了访问父亲的父亲结点。
        public RBTreeNode(E data) {
            this.data = data;
            this.color = RED;
            this.parent = this.lChild = this.rChild = null;
        }

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
                    "color=" + (color ? "BLACK" : "RED") +
                    ", data=" + data +
                    ", parent=" + (parent == null ? "" : parent.hashCode()) +
                    ", code=" + hashCode() +
                    '}';
        }
    }


	//使用静态方法，防止对象为空时访问。默认叶子节点是为空的。空节点为黑色。
    private static <E extends Comparable<E>> void setColor(RBTreeNode<E> p, boolean color) {
        if (p == null)
            return;
        p.color = color;
    }

    private static <E extends Comparable<E>> boolean colorOf(RBTreeNode<E> p) {
        return p == null ? BLACK : p.color;
    }


    private static <E extends Comparable<E>> RBTreeNode<E> leftOf(RBTreeNode<E> p) {
        return p == null ? null : p.lChild;
    }

    private static <E extends Comparable<E>> RBTreeNode<E> rightOf(RBTreeNode<E> p) {
        return p == null ? null : p.rChild;
    }

    private static <E extends Comparable<E>> RBTreeNode<E> parentOf(RBTreeNode<E> p) {
        return p == null ? null : p.parent;
    }

	//左旋
    private void leftRotate(RBTreeNode<E> x) {
        if (x != null) {
            RBTreeNode<E> y = x.rChild;

            //修改y的lChild 指向x
            x.rChild = y.lChild;
            if (y.lChild != null) {
                y.lChild.parent = x;
            }

            //修改x父亲指向x的关系 fax<----->x  fax<---->y
            y.parent = x.parent;
            if (x.parent == null) {
                mRoot = y;
            } else if (x.parent.lChild == x) {
                x.parent.lChild = y;
            } else {
                x.parent.rChild = y;
            }

            //建立 yx指向关系
            y.lChild = x;
            x.parent = y;
        }
    }

	//右旋
    private void rightRotate(RBTreeNode<E> x) {
        if (x != null) {
            RBTreeNode<E> y = x.lChild;

            //修改x左结点指向 y的右孩子
            x.lChild = y.rChild;
            if (y.rChild != null) {
                y.rChild.parent = x;
            }

            //修改y的右结点为x
            y.parent = x.parent;
            if (x.parent == null) {
                mRoot = y;
            } else if (x.parent.lChild == x) {
                x.parent.lChild = y;
            } else {
                x.parent.rChild = y;
            }

            //修改y的右结点指向x
            y.rChild = x;
            x.parent = y;
        }
    }

	//插入元素
    public void insert(E data) {
        if (data == null)
            throw new NullPointerException();
        int cmp;
        RBTreeNode<E> x = mRoot;
        RBTreeNode<E> y = null;
        while (x != null) {
            y = x;
            cmp = data.compareTo(x.data);
            x = cmp < 0 ? x.lChild : x.rChild;
        }

        RBTreeNode<E> node = new RBTreeNode<>(data);
        node.parent = y;
        if (y != null) {
            cmp = data.compareTo(y.data);
            if (cmp < 0) {
                y.lChild = node;
            } else {
                y.rChild = node;
            }
        } else {
            mRoot = node;
            setColor(mRoot, BLACK);
        }
        //默认节点新建是红色
		//调整颜色和位置，保证满足红黑树的五条性质。当增加一个红色节点时，会破坏性质4
        fixAfterInsertion(node);
        size++;
    }

    /**
     * 插入元素之后平衡红黑树
     * @param node
     */
    private void fixAfterInsertion(RBTreeNode<E> node) {
        //若 父节点存在，并且父节点的颜色是红色
        while (node != null && node != mRoot && colorOf(parentOf(node)) == RED) {
            //若 "父节点"是"祖父节点的左孩子"
            if (parentOf(node) == leftOf(parentOf(parentOf(node)))) {
                RBTreeNode<E> uncle = rightOf(parentOf(parentOf(node)));
                /**
                 * case 1 :
                 * 当前节点和父节点都为红色，破坏性质4
                 * 叔叔节点是红色 父节点是红色，
                 * 由性质4可得 祖父节点是黑色。
                 * 祖父 - 父 - 当 (黑 -> 红  -> 红)
                 * 交换 祖父节点 和 (父节点、叔节点)颜色
                 * 祖父 - 父 - 当 (红 -> 黑  -> 红)  (当前祖父向下链满足性质4)
                 * 向上回溯 将祖父节点作为当前起点
                 */
                if (colorOf(uncle) == RED) {
                    setColor(uncle, BLACK);
                    setColor(parentOf(node), BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    node = parentOf(parentOf(node));
                } else {
                    /**
                     * case 2 : 当前节点是右孩子
                     * 叔叔节点是黑色，父亲节点是红色               黑
                     * 由性质4可得 祖父节点也是黑色             红       黑        ->父节点进行左旋 改变形状为case3
                     * 祖父 - 父 - 当 (黑 -> 红 -> 红)      t1  红(当) t2  t3
                     * 由性质4可以得到t1是黑色。
                     * 此时只能通过左旋父节点 转变为状态3 再进行调整。
                     * 当前节点向上移为父节点
                     */
                    if (rightOf(parentOf(node)) == node) {
                        node = parentOf(node);
                        leftRotate(parentOf(node));
                    }
                    /**
                     * case 3 : 当前节点是左孩子
                     * 叔叔节点是黑色，父亲节点是红色                黑
                     * 由性质4可得 祖父节点也是黑色              红       黑        ->组父节点进行右旋
                     * 祖父 - 父 - 当 (黑 -> 红 -> 红)      红(当) t1  t2  t3
                     * 此时通过调整颜色，右旋祖父节点保证红黑树性质
                     */
                    setColor(parentOf(node), BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    rightRotate(parentOf(parentOf(node)));
                }
            } else {
                // 镜像case1,2,3
                //case 4 :叔叔节点是红色
                RBTreeNode<E> uncle = leftOf(parentOf(parentOf(node)));
                if (colorOf(uncle) == RED) {
                    setColor(uncle, BLACK);
                    setColor(parentOf(node), BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    node = parentOf(parentOf(node));
                } else {
                    //case 5: 叔叔节点是黑色，且当前节点是左孩子
                    if (leftOf(parentOf(node)) == node) {
                        node = parentOf(node);
                        leftRotate(parentOf(node));
                    }
                    //case 6条件:叔叔是黑色，且当前结点是右孩子
                    setColor(parentOf(node), BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    leftRotate(parentOf(parentOf(node)));
                }
            }
        }
        setColor(mRoot, BLACK);
    }


    public void remove(E data) {
        if (data == null)
            throw new NullPointerException();
        RBTreeNode<E> node = search(data);
        if (node == null)
            return;
        remove(node);
    }


    protected int getHeight() {
        return getHeight(mRoot);
    }

    private int getHeight(RBTreeNode<E> node) {
        if (node == null) {
            return 0;
        }
        int l = getHeight(node.lChild);
        int r = getHeight(node.rChild);
        return Math.max(l, r) + 1;
    }

    /**
     * 计算当前节点的替换节点(交换值不会影响二叉搜索树的定义节点)
     * @param node
     * @return
     */
    private RBTreeNode<E> successor(RBTreeNode<E> node) {
        if (node == null)
            return null;
        else if (node.rChild != null) {
            RBTreeNode<E> replace = node.rChild;
            while (replace.lChild != null) {
                replace = replace.lChild;
            }
            return replace;
        } else {
            RBTreeNode<E> replace = node.lChild;
            while (replace.rChild != null) {
                replace = replace.rChild;
            }
            return replace;
        }
    }

    /**
     * 移除一个元素
     * @param node
     */
    private void remove(RBTreeNode<E> node) {
        size--;
        if (node.rChild != null && node.lChild != null) {
            //寻找取代节点
            RBTreeNode<E> replaceNode = successor(node);
            node.data = replaceNode.data;
            node = replaceNode;
        }
        RBTreeNode<E> replace = (node.lChild == null ? node.rChild : node.lChild);
        if (replace != null) {
            //删除点p只有一颗子树非空
            if (node.parent == null) {
                mRoot = replace;
            } else if (node == node.parent.lChild) {
                node.parent.lChild = replace;
            } else {
                node.parent.rChild = replace;
            }
            //删除引用关系
            node.lChild = node.rChild = node.parent = null;
            if (node.color == BLACK) {
                fixAfterDeletion(replace);
            }
        } else if (node.parent == null) {
            //删除的节点是根节点
            mRoot = null;
        } else {
            //删除节点左右子树都为空
            if (node.color == BLACK)
                fixAfterDeletion(node);
            if (node.parent != null) {
                if (node == node.parent.lChild) {
                    node.parent.lChild = null;
                } else if (node == node.parent.rChild) {
                    node.parent.rChild = null;
                }
                node.parent = null;
            }
        }
    }

    /**
     * 删除元素之后平衡
     * @param x
     */
    private void fixAfterDeletion(RBTreeNode<E> x) {
        while (x != mRoot && colorOf(x) == BLACK) {
            if (x == leftOf(parentOf(x))) {
                RBTreeNode<E> brother = rightOf(parentOf(x));
                if (colorOf(brother) == RED) {
                    //情况1 兄弟节点是红色
                    setColor(brother, BLACK);
                    setColor(parentOf(x), RED);

                    leftRotate(parentOf(x));
                    brother = rightOf(parentOf(x));
                }
                if (colorOf(leftOf(brother)) == BLACK &&
                        colorOf(rightOf(brother)) == BLACK) {
                    //情况2 兄弟节点是黑色 兄弟结点的左右孩子都是黑色
                    setColor(brother, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(rightOf(brother)) == BLACK) {
                        //情况3 兄弟节点是黑色 兄弟结点的右孩子是黑色

                        setColor(leftOf(brother), BLACK);
                        setColor(brother, RED);
                        rightRotate(brother);
                        brother = rightOf(parentOf(x));
                    }
                    //情况4
                    setColor(brother, colorOf(parentOf(x)));

                    setColor(parentOf(x), BLACK);
                    setColor(rightOf(brother), BLACK);
                    leftRotate(parentOf(x));
                    x = mRoot;
                }
            } else {
                //与之前成镜像关系
                RBTreeNode<E> brother = x.parent.lChild;
                if (colorOf(brother) == RED) {
                    //情况5 兄弟节点是红色
                    setColor(brother, BLACK);

                    setColor(parentOf(x), RED);
                    rightRotate(parentOf(x));
                    brother = leftOf(parentOf(x));
                }
                if (colorOf(leftOf(brother)) == BLACK &&
                        colorOf(rightOf(brother)) == BLACK) {
                    //情况6 兄弟节点是黑色 兄弟节点的左右孩子是黑色
                    setColor(brother, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(leftOf(brother)) == BLACK) {
                        //情况7 兄弟节点是黑色 兄弟结点 左右孩子都是黑色
                        setColor(rightOf(brother), BLACK);
                        setColor(brother, RED);
                        rightRotate(brother);
                        brother = leftOf(parentOf(x));
                    }
                    //情况8

                    setColor(brother, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(leftOf(brother), BLACK);
                    rightRotate(parentOf(x));
                    x = mRoot;
                }
            }
        }
        setColor(x, BLACK);
    }

    /**
     * 查找一个元素
     * @param data
     * @return
     */
    public RBTreeNode<E> search(E data) {
        if (data == null)
            throw new NullPointerException();
        RBTreeNode<E> node = mRoot;
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
        return null;
    }

//    TreeMap


    /**
     * 层序遍历输出打印
     * TODO 完善图像显示
     */
    public void levelTraversal() {
        Queue<RBTreeNode<E>> queue = new LinkedList<>();
        queue.add(mRoot);
        RBTreeNode<E> node;
        RBTreeNode<E> last = mRoot;
        RBTreeNode<E> nlast = mRoot;
        while (!queue.isEmpty()) {
            node = queue.poll();
            System.out.print(node + "   ");
            if (node.lChild != null) {
                queue.add(node.lChild);
                nlast = node.lChild;
            }
            if (node.rChild != null) {
                queue.add(node.rChild);
                nlast = node.rChild;
            }
            if (last == node) {
                System.out.println();
                last = nlast;
            }
        }
    }

    private void mid() {
        mid(mRoot);
    }

    private void mid(RBTreeNode<E> node) {
        if (node == null)
            return;
        mid(node.lChild);
        System.out.print(node.data + " ");
        mid(node.rChild);
    }

    public static void main(String[] args) {
        int[] array = new int[]{1,2,3,4,5,6,7,7,7,7,7};
        RBTree<Integer> rbTree = new RBTree<Integer>();
        for (int data : array){
            rbTree.insert(data);
        }
        rbTree.mid();
        System.out.println();
        System.out.println(rbTree.getHeight());
//        rbTree.levelTraversal();
        rbTree.remove(5);
        rbTree.mid();
        System.out.println();
        System.out.println(rbTree.getHeight());
    }
}
