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
