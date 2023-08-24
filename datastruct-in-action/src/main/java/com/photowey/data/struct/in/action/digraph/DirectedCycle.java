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

/**
 * {@code DirectedCycle}
 *
 * @author photowey
 * @date 2022/11/03
 * @since 1.0.0
 */
public class DirectedCycle {

    // 索引代表顶点,值表示当前顶点是否已经被搜索
    private boolean[] marked;
    // 记录图中是否有环
    private boolean hasCycle;
    // 索引代表顶点,使用栈的思想,记录当前顶点有没有已经处于正在搜索的有向路径上
    private final boolean[] onStack;

    // 创建一个检测环对象,检测图 G 中是否有环
    public DirectedCycle(Digraph digraph) {
        // 初始化marked 数组
        this.marked = new boolean[digraph.points()];
        // 初始化 hasCycle
        this.hasCycle = false;
        // 初始化 onStack 数组
        this.onStack = new boolean[digraph.points()];

        // 找到图中每一个顶点,让每一个顶点作为入口,调用一次 dfs 进行搜索
        for (int v = 0; v < digraph.points(); v++) {
            // 判断如果当前顶点还没有搜索过,则调用dfs进行搜索
            if (!this.marked[v]) {
                this.dfs(digraph, v);
            }
        }
    }

    // 基于深度优先搜索,检测图 G 中是否有环
    private void dfs(Digraph digraph, int v) {
        // 把顶点 v 表示为已搜索
        this.marked[v] = true;
        // 把当前顶点进栈
        this.onStack[v] = true;

        for (Integer w : digraph.adj(v)) {
            // 判断如果当前顶点 w 没有被搜索过,则继续递归调用 dfs 方法完成深度优先搜索
            if (!this.marked[w]) {
                this.dfs(digraph, w);
            }

            // 判断当前顶点 w 是否已经在栈中,如果已经在栈中,证明当前顶点之前处于正在搜索的状态,那么现在又要搜索一次,证明检测到环了
            if (this.onStack[w]) {
                this.hasCycle = true;
                return;
            }
        }
        // 把当前顶点出栈
        this.onStack[v] = false;
    }

    // 判断当前有向图 G 中是否有环
    public boolean hasCycle() {
        return this.hasCycle;
    }
}
