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
 * {@code Graph}
 *
 * @author photowey
 * @date 2022/11/03
 * @since 1.0.0
 */
public class Graph {

    // 顶点数目
    private final int points;
    // 边的数目
    private int edges;
    // 邻接表,队列的形式
    private Queue<Integer>[] adj;

    public Graph(int points) {
        //  初始化顶点数量
        this.points = points;
        // 初始化边的数量
        this.edges = 0;
        // 初始化邻接表
        this.adj = new Queue[points];
        // 初始化邻接表中的空队列
        for (int i = 0; i < this.adj.length; i++) {
            this.adj[i] = new ArrayDeque<>();
        }
    }

    public void addEdge(int v, int w) {
        // 把w添加到v的链表中,这样顶点v就多了一个相邻点w
        this.adj[v].add(w);
        // 把v添加到w的链表中,这样顶点w就多了一个相邻点v
        this.adj[w].add(v);
        // 边的数目自增1
        this.edges++;
    }

    // 获取顶点数目
    public int points() {
        return this.points;
    }

    // 获取边的数目
    public int edges() {
        return this.edges;
    }

    // 获取和顶点v相邻的所有顶点
    public Queue<Integer> adj(int v) {
        return this.adj[v];
    }
}