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
package com.photowey.data.struct.in.action.graph;

import org.junit.jupiter.api.Test;

/**
 * {@code DepthFirstSearchTest}
 *
 * @author photowey
 * @date 2022/11/03
 * @since 1.0.0
 */
class DepthFirstSearchTest {

    @Test
    public void test() {
        // 准备 Graph 对象
        Graph graph = new Graph(13);
        graph.addEdge(0, 5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 6);
        graph.addEdge(5, 3);
        graph.addEdge(5, 4);
        graph.addEdge(3, 4);
        graph.addEdge(4, 6);
        graph.addEdge(7, 8);
        graph.addEdge(9, 11);
        graph.addEdge(9, 10);
        graph.addEdge(9, 12);
        graph.addEdge(11, 12);

        // 准备深度优先搜索对象
        DepthFirstSearch search = new DepthFirstSearch(graph, 0);
        // 测试与某个顶点相通的顶点数量
        int count = search.count();
        System.out.println("与起点0相通的顶点的数量为:" + count);
        // 测试某个顶点与起点是否相同
        boolean marked1 = search.marked(5);
        System.out.println("顶点5和顶点0是否相通：" + marked1);
        boolean marked2 = search.marked(7);
        System.out.println("顶点7和顶点0是否相通：" + marked2);
    }
}