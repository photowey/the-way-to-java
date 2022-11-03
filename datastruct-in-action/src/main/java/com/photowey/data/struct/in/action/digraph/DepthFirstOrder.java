package com.photowey.data.struct.in.action.digraph;

import java.util.Stack;

/**
 * {@code DepthFirstOrder}
 *
 * @author photowey
 * @date 2022/11/03
 * @since 1.0.0
 */
public class DepthFirstOrder {
    
    // 索引代表顶点,值表示当前顶点是否已经被搜索
    private boolean[] marked;
    // 使用栈,存储顶点序列
    private Stack<Integer> reversePost;

    // 创建一个检测环对象,检测图 G 中是否有环
    public DepthFirstOrder(Digraph digraph) {
        // 初始化 marked 数组
        this.marked = new boolean[digraph.points()];
        // 初始化 reversePost 栈
        this.reversePost = new Stack<>();

        // 遍历图中的每一个顶点,让每个顶点作为入口,完成一次深度优先搜索
        for (int v = 0; v < digraph.points(); v++) {
            if (!this.marked[v]) {
                this.dfs(digraph, v);
            }
        }
    }

    // 基于深度优先搜索,把顶点排序
    private void dfs(Digraph digraph, int v) {
        // 标记当前 v 已经被搜索
        this.marked[v] = true;
        // 通过循环深度搜索顶点 v
        for (Integer w : digraph.adj(v)) {
            // 如果当前顶点 w 没有搜索,则递归调用 dfs 进行搜索
            if (!this.marked[w]) {
                dfs(digraph, w);
            }
        }
        // 让顶点 v 进栈
        this.reversePost.push(v);
    }

    // 获取顶点线性序列
    public Stack<Integer> reversePost() {
        return this.reversePost;
    }
}