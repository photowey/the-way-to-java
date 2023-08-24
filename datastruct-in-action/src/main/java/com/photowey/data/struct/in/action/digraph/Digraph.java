/*
 * Copyright © 2021 the original author or authors.
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
package com.photowey.data.struct.in.action.digraph;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * {@code Digraph}
 *
 * @author photowey
 * @date 2022/11/03
 * @since 1.0.0
 */
public class Digraph {

    // 顶点数目
    private final int points;
    // 边的数目
    private int edges;
    // 邻接表
    private Queue<Integer>[] adj;

    public Digraph(int points) {
        // 初始化顶点数量
        this.points = points;
        // 初始化边的数量
        this.edges = 0;
        // 初始化邻接表
        this.adj = new Queue[points];
        for (int i = 0; i < this.adj.length; i++) {
            this.adj[i] = new ArrayDeque<>();
        }
    }

    // 获取顶点数目
    public int points() {
        return this.points;
    }

    // 获取边的数目
    public int edges() {
        return this.edges;
    }

    // 向有向图中添加一条边 v->w
    public void addEdge(int v, int w) {
        // 只需要让顶点 w 出现在顶点 v 的邻接表中,因为边是有方向的,最终,顶点 v 的邻接表中存储的相邻顶点的含义是: v -> 其他顶点
        this.adj[v].add(w);
        this.edges++;
    }

    // 获取由 v 指出的边所连接的所有顶点
    public Queue<Integer> adj(int v) {
        return this.adj[v];
    }

    // 该图的反向图
    private Digraph reverse() {
        // 创建有向图对象
        Digraph digraph = new Digraph(points);

        for (int v = 0; v < points; v++) {
            // 获取由该顶点 v 指出的所有边
            for (Integer w : this.adj[v]) { // 原图中表示的是由顶点 v -> w 的边
                digraph.addEdge(w, v); // w -> v
            }
        }

        return digraph;
    }
}