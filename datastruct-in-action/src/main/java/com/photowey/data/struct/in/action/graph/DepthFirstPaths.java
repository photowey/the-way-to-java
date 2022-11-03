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

import java.util.Stack;

/**
 * {@code DepthFirstPaths}
 *
 * @author photowey
 * @date 2022/11/03
 * @since 1.0.0
 */
public class DepthFirstPaths {

    // 索引代表顶点,值表示当前顶点是否已经被搜索
    private boolean[] marked;
    // 起点
    private int s;
    // 索引代表顶点,值代表从起点 s 到当前顶点路径上的最后一个顶点
    private int[] edgeTo;

    // 构造深度优先搜索对象,使用深度优先搜索找出 G 图中起点为 s 的所有路径
    public DepthFirstPaths(Graph graph, int s) {
        // 初始化 marked 数组
        this.marked = new boolean[graph.points()];
        // 初始化起点
        this.s = s;
        // 初始化 edgeTo 数组
        this.edgeTo = new int[graph.points()];

        this.dfs(graph, s);
    }

    // 使用深度优先搜索找出 G 图中 v 顶点的所有相邻顶点
    private void dfs(Graph graph, int v) {
        // 把v表示为已搜索
        this.marked[v] = true;

        // 遍历顶点v的邻接表,拿到每一个相邻的顶点,继续递归搜索
        for (Integer w : graph.adj(v)) {
            // 如果顶点w没有被搜索,则继续递归搜索
            if (!this.marked[w]) {
                this.edgeTo[w] = v;// 到达顶点w的路径上的最后一个顶点是v
                this.dfs(graph, w);
            }

        }
    }

    // 判断 w 顶点与 s 顶点是否存在路径
    public boolean hasPathTo(int v) {
        return this.marked[v];
    }

    // 找出从起点 s 到顶点 v 的路径(就是该路径经过的顶点)
    public Stack<Integer> pathTo(int v) {
        if (!this.hasPathTo(v)) {
            return null;
        }

        // 创建栈对象,保存路径中的所有顶点
        Stack<Integer> path = new Stack<>();

        // 通过循环,从顶点 v 开始,一直往前找,到找到起点为止
        for (int x = v; x != this.s; x = this.edgeTo[x]) {
            path.push(x);
        }

        // 把起点 s 放到栈中
        path.push(s);

        return path;
    }
}