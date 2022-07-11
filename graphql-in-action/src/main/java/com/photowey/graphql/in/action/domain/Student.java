package com.photowey.graphql.in.action.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * {@code Student}
 *
 * @author photowey
 * @date 2022/07/11
 * @since 1.0.0
 */
@Entity
@Data
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String email;

     @ManyToOne(fetch = FetchType.EAGER)
    private School school;

    public Student(String name, String email, School school) {
        this.name = name;
        this.email = email;
        this.school = school;
    }
}
