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
package com.photowey.jvm.in.action.hello;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * {@code Hello}
 *
 * @author photowey
 * @date 2022/10/04
 * @since 1.0.0
 */
public class Hello {

    public static void main(String[] args) {
        Hello hello = new Hello();
        LocalDateTime now = LocalDateTime.now();
        int count = 10;
        System.out.println(count);
    }

    public static void testStatic() {
        Hello hello = new Hello();
        LocalDateTime now = LocalDateTime.now();
        int count = 10;
        System.out.println(count);
    }

    public void testThis() {
        Hello hello = new Hello();
        LocalDateTime now = LocalDateTime.now();
        int count = 10;
        System.out.println(count);
    }

    public void m1() {
        LocalDateTime now = LocalDateTime.now();
        String name = "photowey";
        this.m3(now, name);
        System.out.println(now + name);
    }

    public void m2() {
        LocalDateTime now = LocalDateTime.now();
        String name = "photowey";
        String rvt = this.m3(now, name);
        System.out.println(now + name + rvt);
    }

    public String m3(LocalDateTime now, String name) {
        double price = 10.24D;
        char gender = '男';
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(now) + "@" + name + "@" + price + "@" + gender;
    }

    public void slotReuse() {
        int a = 0;
        {
            int b = 0;
            b = a + 1;
        }

        int c = a + 1;
    }
}
