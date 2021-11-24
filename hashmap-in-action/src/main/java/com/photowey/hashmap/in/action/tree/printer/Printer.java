package com.photowey.hashmap.in.action.tree.printer;

public abstract class Printer {
    /**
     * 二叉树的基本信息
     */
    protected IBinaryTree tree;

    public Printer(IBinaryTree tree) {
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
        print();
        System.out.println();
    }

    /**
     * 打印
     */
    public void print() {
        System.out.print(printString());
    }
}
