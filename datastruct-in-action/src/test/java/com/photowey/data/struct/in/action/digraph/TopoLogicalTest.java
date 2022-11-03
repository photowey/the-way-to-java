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
package com.photowey.data.struct.in.action.digraph;

import org.junit.jupiter.api.Test;

import java.util.Stack;

/**
 * {@code TopoLogicalTest}
 *
 * @author weichangjun
 * @date 2022/11/03
 * @since 1.0.0
 */
class TopoLogicalTest {

    @Test
    void testTopo() {
        // 准备有向图
        Digraph digraph = new Digraph(6);
        digraph.addEdge(0, 2);
        digraph.addEdge(0, 3);
        digraph.addEdge(2, 4);
        digraph.addEdge(3, 4);
        digraph.addEdge(4, 5);
        digraph.addEdge(1, 3);

        // 通过 TopoLogical 对象堆有向图中的顶点进行排序
        TopoLogical topoLogical = new TopoLogical(digraph);

        // 获取顶点的线性序列进行打印
        Stack<Integer> order = topoLogical.order();
        StringBuilder topo = new StringBuilder();
        while (order.size() != 0) {
            topo.append(order.pop()).append("->");
        }

        String str = topo.toString();
        int index = str.lastIndexOf("->");
        str = str.substring(0, index);

        System.out.println(str);
    }

}
