package com.photowey.hashmap.in.action.tree;

import com.photowey.hashmap.in.action.tree.printer.BinaryTrees;
import org.junit.jupiter.api.Test;

/**
 * {@code BSTTest}
 *
 * @author photowey
 * @date 2021/11/27
 * @since 1.0.0
 */
class BSTTest {

    /**
     * @see * https://520it.com/binarytrees/
     */
    @Test
    void testBST() {
        Integer[] data = new Integer[]{55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50};

        BST<Integer> rb = new BST<>();
        for (int i = 0; i < data.length; i++) {
            rb.add(data[i]);
            System.out.println("+[" + data[i] + "]");
            BinaryTrees.println(rb);
            System.out.println("---------------------------------------");
        }
    }
}