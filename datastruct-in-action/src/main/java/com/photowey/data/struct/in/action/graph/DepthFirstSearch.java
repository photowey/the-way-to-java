/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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