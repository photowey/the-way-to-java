package com.photowey.graphql.in.action.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * {@code School}
 *
 * @author photowey
 * @date 2022/07/11
 * @since 1.0.0
 */
@Entity
@Data
@NoArgsConstructor
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @OneToMany(fetch=FetchType.EAGER)
    private List<Student> students;


    public School(String name) {
        this.name = name;
    }

}