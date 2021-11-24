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
package com.photowey.hashmap.in.action.tree.printer;

/**
 * @author MJ Lee
 */
public final class BinaryTrees {

    private BinaryTrees() {
    }

    public static void print(IBinaryTree tree) {
        print(tree, null);
    }

    public static void println(IBinaryTree tree) {
        println(tree, null);
    }

    public static void print(IBinaryTree tree, PrintStyle style) {
        if (tree == null || tree.root() == null) return;
        printer(tree, style).print();
    }

    public static void println(IBinaryTree tree, PrintStyle style) {
        if (tree == null || tree.root() == null) return;
        printer(tree, style).println();
    }

    public static String printString(IBinaryTree tree) {
        return printString(tree, null);
    }

    public static String printString(IBinaryTree tree, PrintStyle style) {
        if (tree == null || tree.root() == null) return null;
        return printer(tree, style).printString();
    }

    private static Printer printer(IBinaryTree tree, PrintStyle style) {
        if (style == PrintStyle.INORDER) return new InorderPrinter(tree);
        return new LevelOrderPrinter(tree);
    }

    public enum PrintStyle {
        LEVEL_ORDER, INORDER
    }
}