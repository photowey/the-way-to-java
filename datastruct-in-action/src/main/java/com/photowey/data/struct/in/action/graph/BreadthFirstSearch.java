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

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * {@code BreadthFirstSearch}
 * <p>
 * 所谓的广度优先搜索,指的是在搜索时,
 * 如果遇到一个结点既有子结点,又有兄弟结点,那么先找兄弟结点,然后找子结点。
 *
 * @author photowey
 * @date 2022/11/03
 * @since 1.0.0
 */
public class BreadthFirstSearch {

    // 索引代表顶点,值表示当前顶点是否已经被搜索
    private boolean[] marked;
    // 记录有多少个顶点与 s 顶点相通
    private int count;
    // 用来存储待搜索邻接表的点
    private Queue<Integer> waitSearch;

    // 构造广度优先搜索对象,使用广度优先搜索找出 G 图中 s 顶点的所有相邻顶点
    public BreadthFirstSearch(Graph graph, int s) {
        this.marked = new boolean[graph.points()];
        this.count = 0;
        this.waitSearch = new ArrayDeque<>();

        this.bfs(graph, s);
    }

    // 使用广度优先搜索找出 G 图中 v 顶点的所有相邻顶点
    private void bfs(Graph graph, int v) {
        // 把当前顶点 v 标识为已搜索
        this.marked[v] = true;
        // 让顶点 v 进入队列,待搜索
        this.waitSearch.add(v);
        // 通过循环,如果队列不为空,则从队列中弹出一个待搜索的顶点进行搜索
        while (!this.waitSearch.isEmpty()) {
            // 弹出一个待搜索的顶点
            Integer wait = this.waitSearch.poll();
            // 遍历 wait 顶点的邻接表
            for (Integer w : graph.adj(wait)) {
                if (!this.marked[w]) {
                    this.bfs(graph, w);
                }
            }
        }

        // 让相通的顶点 +1；
        this.count++;
    }

    // 判断 w 顶点与 s 顶点是否相通
    public boolean marked(int w) {
        return this.marked[w];
    }

    // 判断 w 顶点与 s 顶点是否不相通
    public boolean isNotMarked(int w) {
        return !this.marked(w);
    }

    // 获取与顶点 s 相通的所有顶点的总数
    public int count() {
        return this.count;
    }
}