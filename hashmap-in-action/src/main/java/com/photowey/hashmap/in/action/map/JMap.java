/*
 * Copyright (c) 1997, 2018, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package com.photowey.hashmap.in.action.map;

import java.io.Serializable;

/**
 * {@code JMap}
 * <p>
 * {@link JMap} ~= {@link java.util.HashMap}
 * 完全是为了学习,能给源码写注释而生.
 *
 * @author photowey
 * @date 2021/11/21
 * @since 1.0.0
 */
public class JMap<K, V> implements Serializable {

    /**
     * 解决 Hash 碰撞
     * 1.重 Hash
     * 2.开放地址
     * 3.溢出区(开辟共同溢出区)
     * 4.链地址法(HashMap)
     * <p>
     * 如果: 确实产生了 Hash 碰撞
     * 1.扩容
     * 2.数据结构
     */
    public static void main(String[] args) {
        System.out.println("Hello JMap!");
    }
}
