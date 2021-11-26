package com.photowey.hashmap.in.action.tree.printer;

/**
 * @author MJ Lee
 */
public final class BinaryTrees {

    private BinaryTrees() {
    }

    public static <E> void print(IPrintableTree<E> tree) {
        print(tree, PrintStyle.LEVEL_ORDER);
    }

    public static <E> void println(IPrintableTree<E> tree) {
        println(tree, PrintStyle.LEVEL_ORDER);
    }

    public static <E> void print(IPrintableTree<E> tree, PrintStyle style) {
        if (tree == null || tree.root() == null) {
            return;
        }

        printer(tree, style).print();
    }

    public static <E> void println(IPrintableTree<E> tree, PrintStyle style) {
        if (tree == null || tree.root() == null) {
            return;
        }

        printer(tree, style).println();
    }

    public static <E> String printString(IPrintableTree<E> tree) {
        return printString(tree, PrintStyle.LEVEL_ORDER);
    }

    public static <E> String printString(IPrintableTree<E> tree, PrintStyle style) {
        if (tree == null || tree.root() == null) {
            return null;
        }

        return printer(tree, style).printString();
    }

    private static <E> Printer<E> printer(IPrintableTree<E> tree, PrintStyle style) {
        if (style == PrintStyle.INORDER) {
            return new InorderPrinter<>(tree);
        }

        return new LevelOrderPrinter<>(tree);
    }

    public enum PrintStyle {
        LEVEL_ORDER,
        INORDER
    }
}