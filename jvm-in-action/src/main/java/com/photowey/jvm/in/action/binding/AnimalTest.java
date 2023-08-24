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
package com.photowey.jvm.in.action.binding;

/**
 * {@code AnimalTest}
 *
 * @author photowey
 * @date 2022/10/05
 * @since 1.0.0
 */
public class AnimalTest {

    public void showAnimal(Animal animal) {
        animal.eat();//表现为: 晚期绑定
    }

    public void showHunt(Huntable h) {
        h.hunt();//表现为: 晚期绑定
    }

}
