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
package com.photowey.mybatis.in.action;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.photowey.mybatis.in.action.annotation.EnablePersistence;

@EnablePersistence
// @SpringBootApplication
public class Mybatis {

    public static void main(String[] args) {
        // SpringApplication.run(Mybatis.class, args);
        System.out.println(IdWorker.getId());
    }

}
