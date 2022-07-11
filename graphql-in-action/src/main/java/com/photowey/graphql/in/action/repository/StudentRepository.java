package com.photowey.graphql.in.action.repository;

import com.photowey.graphql.in.action.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@code StudentRepository}
 *
 * @author photowey
 * @date 2022/07/11
 * @since 1.0.0
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
