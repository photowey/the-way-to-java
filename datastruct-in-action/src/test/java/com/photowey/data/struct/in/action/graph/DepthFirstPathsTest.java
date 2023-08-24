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

import java.util.Stack;

/**
 * {@code DepthFirstPathsTest}
 *
 * @author photowey
 * @date 2022/11/03
 * @since 1.0.0
 */
class DepthFirstPathsTest {

    @Test
    public void test() {
        // 城市数量
        int totalNumber = 20;
        Graph graph = new Graph(totalNumber);
        // 添加城市的交通路线
        graph.addEdge(0, 1);
        graph.addEdge(6, 9);
        graph.addEdge(1, 8);
        graph.addEdge(1, 12);
        graph.addEdge(8, 12);
        graph.addEdge(6, 10);
        graph.addEdge(4, 8);

        DepthFirstPaths depthFirstPaths = new DepthFirstPaths(graph, 0);
        Stack<Integer> path = depthFirstPaths.pathTo(12);

        StringBuilder expect = new StringBuilder();
        // 遍历栈对象
        for (Integer v : path) {
            expect.append(v).append("->");
        }

        String pathx = expect.toString().replaceAll("->$", "");
        System.out.println(pathx);
    }
}