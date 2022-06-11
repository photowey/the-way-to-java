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
package com.photowey.sqlite.in.action.repository;

import com.photowey.sqlite.in.action.domain.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code StudentRepositoryTest}
 *
 * @author photowey
 * @date 2022/01/12
 * @since 1.0.0
 */
@SpringBootTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void testSave() {
        Student student = new Student();
        student.setName("photowey");
        student.setAge(18);

        this.studentRepository.insert(student);

        // id == 1481267002722033666
    }

    @Test
    void testQuery() {
        Long id = 1481267002722033666L;
        Student student = this.studentRepository.selectById(id);

        Assertions.assertNotNull(student);
        Assertions.assertEquals("photowey", student.getName());
        Assertions.assertEquals(18, student.getAge());

    }

    @Test
    void testUpdate() {
        Long id = 1481267002722033666L;
        Student student = this.studentRepository.selectById(id);
        Assertions.assertNotNull(student);
        Assertions.assertEquals("photowey", student.getName());
        Assertions.assertEquals(18, student.getAge());

        student.setName("photoshark");
        student.setAge(20);

        this.studentRepository.updateById(student);

        Student student2 = this.studentRepository.selectById(id);

        Assertions.assertNotNull(student2);
        Assertions.assertEquals("photoshark", student2.getName());
        Assertions.assertEquals(20, student2.getAge());

    }

    @Test
    void testDelete() {
        Student student = new Student();
        student.setName("delete");
        student.setAge(20);

        this.studentRepository.insert(student);

        Long id = student.getId();
        Student student2 = this.studentRepository.selectById(id);

        Assertions.assertNotNull(student2);
        Assertions.assertEquals("delete", student2.getName());
        Assertions.assertEquals(20, student2.getAge());

        this.studentRepository.deleteById(student);
        Student student3 = this.studentRepository.selectById(id);

        Assertions.assertNull(student3);

    }

    @Test
    void testFindOne() {
        Long id = 1481267002722033666L;
        Student student = this.studentRepository.findOne(id);

        // com.photowey.sqlite.in.action.repository.StudentRepositoryTest.testUpdate
        Assertions.assertNotNull(student);
        Assertions.assertEquals("photoshark", student.getName());
        Assertions.assertEquals(20, student.getAge());

    }

}