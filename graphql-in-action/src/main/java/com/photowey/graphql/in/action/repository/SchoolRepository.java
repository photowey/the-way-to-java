package com.photowey.graphql.in.action.repository;

import com.photowey.graphql.in.action.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@code SchoolRepository}
 *
 * @author photowey
 * @date 2022/07/11
 * @since 1.0.0
 */
@Repository
public interface SchoolRepository  extends JpaRepository<School, Long> {
}
