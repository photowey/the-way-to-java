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
package com.photowey.design.pattern.in.action.visitor;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Client}
 *
 * @author photowey
 * @date 2022/03/04
 * @since 1.0.0
 */
public class Client {

    public static void main(String[] args) {
        v3();
    }

    private static void v1() {
        // ObjectStructure<Student> structure = new ObjectStructure<>();
        // List<Student> students = structure.listStudents();
        List<Student> students = listStudents();
        Visitor showVisitor = new ShowVisitor();

        for (Student student : students) {
            student.accept(showVisitor);
        }
    }

    private static void v2() {
        // ObjectStructure<Student> structure = new ObjectStructure<>();
        List<Student> students = listStudents();

        Visitor showVisitor = new ShowVisitor();
        Visitor sumVisitor = new SumVisitor();
        List<Visitor> visitors = Lists.newArrayList(showVisitor, sumVisitor);

        for (Student student : students) {
            student.accept(visitors);
        }

        System.out.println("The total sum of bachelors : " + ((SumVisitor) sumVisitor).getTotalBachelor());
    }

    private static void v3() {
        ObjectStructure<Student> structure = new ObjectStructure<>();
        Bachelor bachelor = new Bachelor("xxx", "XXX", "100");
        College college = new College("yyy", "YYY", "99");
        structure.add(bachelor);
        structure.add(college);

        Visitor showVisitor = new ShowVisitor();
        Visitor sumVisitor = new SumVisitor();
        List<Visitor> visitors = Lists.newArrayList(showVisitor, sumVisitor);

        structure.fire(visitors);

        System.out.println("The total sum of bachelors : " + ((SumVisitor) sumVisitor).getTotalBachelor());
    }

    public static List<Student> listStudents() {
        List<Student> students = new ArrayList<>();
        Bachelor bachelor = new Bachelor("xxx", "XXX", "100");
        College college = new College("yyy", "YYY", "99");
        students.add(bachelor);
        students.add(college);

        return students;
    }
}
