package com.photowey.hashmap.in.action.tree.printer;

/**
 * {@code IBinaryTree}
 *
 * @author photowey
 * @date 2021/11/24
 * @since 1.0.0
 */
public interface ITree {

    Object root();

    Object left(Object node);

    Object right(Object node);

    Object string(Object node);

    class Node<E> {
        /**
         * 元素值
         */
        public E element;
        /**
         * 左子节点
         */
        public Node<E> left;
        /**
         * 右子节点
         */
        public Node<E> right;
        /**
         * 父亲节点
         */
        public Node<E> parent;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        /**
         * 是否为叶子结点
         *
         * @return
         */
        public boolean isLeaf() {
            return left == null && right == null;
        }

        /**
         * 是否有两个子节点
         *
         * @return
         */
        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        /**
         * 是否为左节点
         *
         * @return
         */
        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        /**
         * 是否为右节点
         *
         * @return
         */
        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        /**
         * 返回兄弟节点
         *
         * @return
         */
        public Node<E> sibling() {
            if (this.isLeftChild()) {
                return parent.right;
            }

            if (this.isRightChild()) {
                return parent.left;
            }

            return null;
        }
    }
}
