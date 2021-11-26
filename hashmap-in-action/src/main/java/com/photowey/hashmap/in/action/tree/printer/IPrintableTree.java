package com.photowey.hashmap.in.action.tree.printer;

public interface IPrintableTree<E> extends ITree<E> {

    Node<E> root();

    Node<E> left(Node<E> node);

    Node<E> right(Node<E> node);

    String string(Node<E> node);
}
