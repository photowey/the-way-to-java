/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.hashmap.in.action.tree;

import com.photowey.hashmap.in.action.tree.printer.BinaryTrees;
import org.junit.jupiter.api.Test;

/**
 * {@code RBTreeTest}
 *
 * @author photowey
 * @date 2021/11/24
 * @since 1.0.0
 */
class RBTreeTest {

    /**
     * @see * https://520it.com/binarytrees/
     */
    @Test
    void testRBTree() {
        Integer[] data = new Integer[]{55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50};

        RBTree<Integer> rb = new RBTree<>();
        for (int i = 0; i < data.length; i++) {
            rb.add(data[i]);
            System.out.println("+[" + data[i] + "]");
            BinaryTrees.println(rb);
            System.out.println("---------------------------------------");
        }
    }
}