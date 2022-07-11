package com.photowey.graphql.in.action.repository;

import com.photowey.graphql.in.action.domain.School;
import com.photowey.graphql.in.action.domain.Student;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * {@code SchoolRepositoryTest}
 *
 * @author photowey
 * @date 2022/07/11
 * @since 1.0.0
 */
@SpringBootTest
class SchoolRepositoryTest {

    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void init() {
        this.schoolRepository.deleteAll();

        School school1 = this.schoolRepository.save(new School("School1"));
        School school2 = this.schoolRepository.save(new School("School2"));
        School school3 = this.schoolRepository.save(new School("School3"));

        this.studentRepository.saveAll(Lists.newArrayList(
                new Student("Student1", "student1@email.com", school1),
                new Student("Student2", "student2@email.com", school1),

                new Student("Student3", "student3@email.com", school2),
                new Student("Student4", "student4@email.com", school2),

                new Student("Student5", "student5@email.com", school3),
                new Student("Student6", "student6@email.com", school3),
                new Student("Student7", "student7@email.com", school3),
                new Student("Student8", "student8@email.com", school3)
        ));
    }

    @Test
    void testSchoolFindAll() {
        List<School> schools = this.schoolRepository.findAll();
        System.out.println(schools);
    }

    @Test
    void testStudentFindAll() {
        List<Student> students = this.studentRepository.findAll();
        System.out.println(students);
    }
}