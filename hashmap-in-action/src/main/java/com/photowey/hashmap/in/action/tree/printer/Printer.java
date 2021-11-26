package com.photowey.hashmap.in.action.tree.printer;

public abstract class Printer<E> {
    /**
     * 二叉树的基本信息
     */
    protected IPrintableTree<E> tree;

    public Printer(IPrintableTree<E> tree) {
        this.tree = tree;
    }

    /**
     * 生成打印的字符串
     */
    public abstract String printString();

    /**
     * 打印后换行
     */
    public void println() {
        this.print();
        System.out.println();
    }

    /**
     * 打印
     */
    public void print() {
        System.out.print(this.printString());
    }
}
