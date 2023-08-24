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
package com.photowey.mongo.in.action.engine;

import com.photowey.mongo.in.action.service.BookService;
import com.photowey.mongo.in.action.service.TemplateBookService;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * {@code ServiceEngine}
 *
 * @author photowey
 * @date 2021/11/24
 * @since 1.0.0
 */
@Component
@Accessors(fluent = true)
public class ServiceEngine implements IServiceEngine {

    @Getter
    @Autowired
    private BookService bookService;

    @Getter
    @Autowired
    private TemplateBookService templateBookService;
}
