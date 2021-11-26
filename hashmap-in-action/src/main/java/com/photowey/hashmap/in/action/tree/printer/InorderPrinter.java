package com.photowey.hashmap.in.action.tree.printer;

/**
 * ┌──800
 * ┌──760
 * │   └──600
 * ┌──540
 * │   └──476
 * │       └──445
 * ┌──410
 * │   └──394
 * 381
 * │     ┌──190
 * │     │   └──146
 * │  ┌──40
 * │  │  └──35
 * └──12
 * └──9
 *
 * @author MJ Lee
 */
public class InorderPrinter<E> extends Printer<E> {

    private static String rightAppend;
    private static String leftAppend;
    private static String blankAppend;
    private static String lineAppend;

    static {
        int length = 2;
        rightAppend = "┌" + Strings.repeat("─", length);
        leftAppend = "└" + Strings.repeat("─", length);
        blankAppend = Strings.blank(length + 1);
        lineAppend = "│" + Strings.blank(length);
    }

    public InorderPrinter(IPrintableTree<E> tree) {
        super(tree);
    }

    @Override
    public String printString() {
        StringBuilder treeBody = new StringBuilder(this.printString(tree.root(), "", "", ""));
        treeBody.deleteCharAt(treeBody.length() - 1);

        return treeBody.toString();
    }

    /**
     * 生成node节点的字符串
     *
     * @param nodePrefix  node那一行的前缀字符串
     * @param leftPrefix  node整棵左子树的前缀字符串
     * @param rightPrefix node整棵右子树的前缀字符串
     * @return
     */
    private String printString(ITree.Node<E> node, String nodePrefix, String leftPrefix, String rightPrefix) {
        ITree.Node<E> left = tree.left(node);
        ITree.Node<E> right = tree.right(node);
        String string = tree.string(node);

        int length = string.length();
        if (length % 2 == 0) {
            length--;
        }
        length >>= 1;

        String nodeString = "";
        if (right != null) {
            rightPrefix += Strings.blank(length);
            nodeString += printString(right, rightPrefix + rightAppend, rightPrefix + lineAppend, rightPrefix + blankAppend);
        }
        nodeString += nodePrefix + string + "\n";
        if (left != null) {
            leftPrefix += Strings.blank(length);
            nodeString += printString(left, leftPrefix + leftAppend, leftPrefix + blankAppend, leftPrefix + lineAppend);
        }

        return nodeString;
    }
}
