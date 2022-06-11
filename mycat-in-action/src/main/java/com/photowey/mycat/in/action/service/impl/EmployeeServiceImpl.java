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
package com.photowey.mycat.in.action.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photowey.mycat.in.action.domain.entity.Employee;
import com.photowey.mycat.in.action.engine.IMybatisEngine;
import com.photowey.mycat.in.action.repository.EmployeeRepository;
import com.photowey.mycat.in.action.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@code EmployeeServiceImpl}
 *
 * @author photowey
 * @date 2021/11/02
 * @since 1.0.0
 */
@Slf4j
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeRepository, Employee> implements EmployeeService {

    @Autowired
    private IMybatisEngine mybatisEngine;

}

