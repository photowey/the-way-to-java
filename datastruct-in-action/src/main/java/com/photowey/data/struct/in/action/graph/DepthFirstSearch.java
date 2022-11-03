package com.photowey.data.struct.in.action.graph;

/**
 * {@code DepthFirstSearch}
 * <p>
 * 所谓的深度优先搜索,指的是在搜索时,
 * 如果遇到一个结点既有子结点,又有兄弟结点,那么先找子结点,然后找兄弟结点。
 *
 * @author photowey
 * @date 2022/11/03
 * @since 1.0.0
 */
public class DepthFirstSearch {

    // 索引代表顶点,值表示当前顶点是否已经被搜索
    private boolean[] marked;
    // 记录有多少个顶点与 s 顶点相通
    private int count;

    // 构造深度优先搜索对象,使用深度优先搜索找出 G 图中 s 顶点的所有相邻顶点
    public DepthFirstSearch(Graph graph, int s) {
        // 创建一个和图的顶点数一样大小的布尔数组
        this.marked = new boolean[graph.points()];
        this.dfs(graph, s);
    }

    // 使用深度优先搜索找出 G 图中 v 顶点的所有相邻顶点
    private void dfs(Graph graph, int v) {
        // 把当前顶点标记为已搜索
        this.marked[v] = true;
        // 遍历 v 顶点的邻接表,得到每一个顶点 w
        for (Integer w : graph.adj(v)) {
            // 遍历 v  顶点的邻接表,得到每一个顶点 w
            if (!this.marked[w]) {
                // 如果当前顶点w没有被搜索过,则递归搜索与 w 顶点相通的其他顶点
                this.dfs(graph, w);
            }
        }

        // 相通的顶点数量 +1
        this.count++;
    }

    //判断 w 顶点与 s 顶点是否相通
    public boolean marked(int w) {
        return this.marked[w];
    }

    //判断 w 顶点与 s 顶点是否不相通
    public boolean isNotMarked(int w) {
        return !this.marked(w);
    }

    public int count() {
        return this.count;
    }
}