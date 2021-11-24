package com.photowey.hashmap.in.action.tree;

import java.util.Comparator;

/**
 * {@code RBTree}
 * 红黑树
 * <p>
 * 添加(添加位置: 12 种):
 * 1.红-黑-红
 * 2.黑-红(黑-左)
 * 3.红-黑(黑-右)
 * 4.黑(黑-左,黑-右)
 * -- ------------------------------------------------
 * 1.如果添加的 parent 为 BLACK
 * -- 满足红黑树,也满足4阶B树的性质
 * -- 不需要做任何处理
 * -- ------------------------------------------------
 * 2.其余8种不满足红黑树的性质-双红
 * -- ------------------------------------------------
 * 2.1.LL|RR
 * 判断条件: uncle 不是 RED
 * 1.parent染成 BLACK, grand 染成 RED
 * 2.grand 进行单旋转
 * ⭐ LL: 右旋
 * ⭐ RR: 左旋
 * -- ------------------------------------------------
 * 2.2.LR|RL
 * 判断条件: uncle 不是 RED
 * 1.自己染成 BLACK, grand 染成 RED
 * 2.进行双旋转 - 添加的子树- 成为根节点
 * ⭐ LR: parent 左旋, grand 右旋
 * ⭐ RL: parent 右旋, grand 左旋
 * -- ------------------------------------------------
 * 3.上溢 LL
 * 判断条件: uncle 是 RED
 * 1.parent,uncle 染成 BLACK
 * 2.grand 向上合并
 * ⭐ 染成红色,当作是系添加的节点,进行处理
 * ⭐ 如果上溢持续到根节点,只需要将根节点染成 BLACK
 * -- ------------------------------------------------
 * 4.上溢 RR
 * 判断条件: uncle 是 RED
 * 1.parent,uncle 染成 BLACK
 * 2.grand 向上合并
 * ⭐ 染成红色,当作是系添加的节点,进行处理
 * -- ------------------------------------------------
 * 5.上溢 LR
 * 判断条件: uncle 是 RED
 * 1.parent,uncle 染成 BLACK
 * 2.grand 向上合并
 * ⭐ 染成红色,当作是系添加的节点,进行处理
 * -- ------------------------------------------------
 * 6.上溢 RL
 * 判断条件: uncle 是 RED
 * 1.parent,uncle 染成 BLACK
 * 2.grand 向上合并
 * ⭐ 染成红色,当作是系添加的节点,进行处理
 *
 * @author photowey
 * @date 2021/11/24
 * @see * https://github.com/szluyu99/Data_Structure_Note
 * @since 1.0.0
 */
public class RBTree<E> extends BBST<E> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    public RBTree() {
        this(null);
    }

    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }

    /**
     * 红黑树的节点类
     * 节点有颜色,RED 或者 BLACK
     */
    private static class RBNode<E> extends Node<E> {
        boolean color = RED;

        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            String prefix = color == RED ? "R_" : "";
            return prefix + element.toString();
        }
    }

    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;

        // 添加的是根节点 或者 上溢到达了根节点
        if (parent == null) {
            black(node);
            return;
        }

        // 如果父节点是黑色,直接返回
        if (isBlack(parent)) return;

        // 叔父节点
        Node<E> uncle = parent.sibling();
        // 祖父节点
        Node<E> grand = red(parent.parent);
        if (isRed(uncle)) { // 叔父节点是红色[B树节点上溢]
            black(parent);
            black(uncle);
            // 把祖父节点当做是新添加的节点
            // 递归
            afterAdd(grand);
            return;
        }

        // 叔父节点不是红色
        if (parent.isLeftChild()) { // L
            if (node.isLeftChild()) { // LL
                black(parent);
            } else { // LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else { // R
            if (node.isLeftChild()) { // RL
                black(node);
                rotateRight(parent);
            } else { // RR
                black(parent);
            }
            rotateLeft(grand);
        }
    }

    @Override
    protected void afterRemove(Node<E> node) {
        // 如果删除的节点是红色
        // 或者 用以取代删除节点的子节点是红色
        if (isRed(node)) {
            black(node);
            return;
        }

        Node<E> parent = node.parent;
        // 删除的是根节点
        if (parent == null) return;

        // 删除的是黑色叶子节点[下溢]
        // 判断被删除的node是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<E> sibling = left ? parent.right : parent.left;
        if (left) { // 被删除的节点在左边,兄弟节点在右边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // 更换兄弟
                sibling = parent.right;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点,父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点,向兄弟节点借元素
                // 兄弟节点的左边是黑色,兄弟要先旋转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        } else { // 被删除的节点在右边,兄弟节点在左边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                // 更换兄弟
                sibling = parent.left;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点,父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点,向兄弟节点借元素
                // 兄弟节点的左边是黑色,兄弟要先旋转
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }

                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }

    /**
     * 染色
     */
    private Node<E> color(Node<E> node, boolean color) {
        if (node == null) return node;
        ((RBNode<E>) node).color = color;
        return node;
    }

    /**
     * 将该节点染为红色
     *
     * @param node
     * @return
     */
    private Node<E> red(Node<E> node) {
        return color(node, RED);
    }

    /**
     * 将该节点染为黑色
     *
     * @param node
     * @return
     */
    private Node<E> black(Node<E> node) {
        return color(node, BLACK);
    }

    /**
     * 返回该节点的颜色
     *
     * @param node
     * @return
     */
    private boolean colorOf(Node<E> node) {
        return node == null ? BLACK : ((RBNode<E>) node).color;
    }

    /**
     * 该节点是否为黑色
     *
     * @param node
     * @return
     */
    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }

    /**
     * 该节点是否为红色
     *
     * @param node
     * @return
     */
    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element, parent);
    }
}
