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
package com.photowey.postgresql.in.action.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.photowey.postgresql.in.action.domain.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * {@code StudentRepositoryTest}
 *
 * @author photowey
 * @date 2022/07/13
 * @since 1.0.0
 */
@SpringBootTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void testSave() {
        runSave();
    }

    private void runSave() {
        this.studentRepository.insert(new Student(9527L, "photoshark", 18));
        this.studentRepository.insert(new Student(9528L, "photowey", 19));
        this.studentRepository.insert(new Student(9529L, "photochili", 20));
        this.studentRepository.insert(new Student(9530L, "sharkchili", 21));
        this.studentRepository.insert(new Student(9531L, "sharkpi", 22));
    }

    @Test
    void testQuery() {
        this.studentRepository.delete(new QueryWrapper<>());
        this.runSave();
        Student student = this.studentRepository.selectById(9527L);
        Assertions.assertNotNull(student);
        Assertions.assertEquals("photoshark", student.getName());
        Assertions.assertEquals(18, student.getAge());
    }

    @Test
    void testPage() {
        this.studentRepository.delete(new QueryWrapper<>());
        this.runSave();
        IPage<Student> page = new Page<>(2, 3);
        this.studentRepository.selectPage(page, new QueryWrapper<>());

        List<Student> records = page.getRecords();
        Assertions.assertEquals(5, page.getTotal());
        Assertions.assertEquals(2, page.getCurrent());
        Assertions.assertEquals(3, page.getSize());
        Assertions.assertEquals(2, records.size());

        Student student = records.get(0);
        Assertions.assertEquals("sharkchili", student.getName());
        Assertions.assertEquals(21, student.getAge());

        page = new Page<>(2, 4);
        this.studentRepository.selectPage(page, new QueryWrapper<>());
    }
}