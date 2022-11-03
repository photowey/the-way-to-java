package com.photowey.data.struct.in.action.digraph;

import java.util.Stack;

/**
 * {@code TopoLogical}
 *
 * @author photowey
 * @date 2022/11/03
 * @since 1.0.0
 */
public class TopoLogical {
    
    // 顶点的拓扑排序
    private Stack<Integer> order;

    // 构造拓扑排序对象
    public TopoLogical(Digraph digraph) {
        // 创建一个检测有向环的对象
        DirectedCycle cycle = new DirectedCycle(digraph);
        // 判断 G 图中有没有环,如果没有环,则进行顶点排序: 创建一个顶点排序对象
        if (!cycle.hasCycle()) {
            DepthFirstOrder depthFirstOrder = new DepthFirstOrder(digraph);
            this.order = depthFirstOrder.reversePost();
        }
    }

    // 判断图 G 是否有环
    private boolean isCycle() {
        return this.order == null;
    }

    // 获取拓扑排序的所有顶点
    public Stack<Integer> order() {
        return this.order;
    }
}