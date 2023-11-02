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
package com.photowey.common.in.action.util;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * {@code LambdaUtilsTest}
 *
 * @author photowey
 * @date 2022/11/28
 * @since 1.0.0
 */
@Slf4j
class LambdaUtilsTest {

 List<Student> studentList = new ArrayList<>();

 @BeforeEach
 void init() {
  studentList.add(new Student(1L, "Tom", 18, Lists.newArrayList(new Hobby(1L, "PingPang"), new Hobby(1L, "Football"))));
  studentList.add(new Student(2L, "Jerry", 18, Lists.newArrayList(new Hobby(2L, "Football"), new Hobby(3L, "Badminton"))));
  studentList.add(new Student(3L, "Tom", 25, Lists.newArrayList(new Hobby(3L, "Badminton"))));
 }

 @Test
 void testToList() {
  List<Long> ids = LambdaUtils.toList(studentList, Student::getId);

  Assertions.assertEquals(3, ids.size());
  Assertions.assertTrue(ids.contains(1L));
  Assertions.assertTrue(ids.contains(2L));
  Assertions.assertTrue(ids.contains(3L));

 }

 @Test
 void testToList_predicate() {
  List<Long> ids = LambdaUtils.toList(studentList, stu -> stu.getAge() < 20, Student::getId);

  Assertions.assertEquals(2, ids.size());
  Assertions.assertTrue(ids.contains(1L));
  Assertions.assertTrue(ids.contains(2L));

 }

 @Test
 void testToList_flatMap() {
  List<Long> ids = LambdaUtils.toListm(studentList, stu -> stu.getHobbies().stream(), Hobby::getId);

  Assertions.assertEquals(5, ids.size());
  Assertions.assertTrue(ids.contains(1L));
  Assertions.assertTrue(ids.contains(2L));
  Assertions.assertTrue(ids.contains(3L));

 }

 @Test
 void testToSet() {
  Set<String> names = LambdaUtils.toSet(studentList, Student::getName);

  Assertions.assertEquals(2, names.size());
  Assertions.assertTrue(names.contains("Tom"));
  Assertions.assertTrue(names.contains("Jerry"));

 }

 @Test
 void testToSet_flatMap() {
  Set<String> names = LambdaUtils.toSetm(studentList, stu -> stu.getHobbies().stream(), Hobby::getName);

  Assertions.assertEquals(3, names.size());
  Assertions.assertTrue(names.contains("PingPang"));
  Assertions.assertTrue(names.contains("Football"));
  Assertions.assertTrue(names.contains("Badminton"));

 }

 @Test
 void testToSet_predicate() {
  Set<String> names = LambdaUtils.toSet(studentList, stu -> stu.getAge() < 20, Student::getName);
  Assertions.assertEquals(2, names.size());
  Assertions.assertTrue(names.contains("Tom"));
  Assertions.assertTrue(names.contains("Jerry"));

 }

 @Test
 void testToMap() {
  List<Student> studentList = new ArrayList<>();
  studentList.add(new Student(1L, "Tom", 18));
  studentList.add(new Student(2L, "Jerry", 18));
  Map<String, Student> studentMap = LambdaUtils.toMap(studentList, Student::getName);

  Assertions.assertEquals(2, studentMap.keySet().size());
  Assertions.assertTrue(studentMap.keySet().contains("Tom"));
  Assertions.assertTrue(studentMap.keySet().contains("Jerry"));

  Map<String, Student> studentMap1 = studentList.stream().collect(Collectors.toMap(Student::getName, Function.identity()));

  Assertions.assertEquals(2, studentMap1.keySet().size());
  Assertions.assertTrue(studentMap1.keySet().contains("Tom"));
  Assertions.assertTrue(studentMap1.keySet().contains("Jerry"));

 }

 @Test
 void testFilter() {
  List<Student> students = LambdaUtils.filter(studentList, (student) -> student.getName().equals("Tom"));

  Assertions.assertEquals(2, students.size());

 }

 @Test
 void testFindAny() {
  Student tom = LambdaUtils.findAny(studentList, (student) -> student.getName().equals("Tom"));

  Assertions.assertEquals(1L, tom.getId());

 }

 @Test
 void testGroupBy() {
  Map<String, List<Student>> map = LambdaUtils.groupingBy(studentList, Student::getName);

  Assertions.assertEquals(2, map.size());
  Assertions.assertEquals(2, map.get("Tom").size());
  Assertions.assertEquals(1, map.get("Jerry").size());

 }

 @Data
 @NoArgsConstructor
 @AllArgsConstructor
 private static class Student implements Serializable {

  private static final long serialVersionUID = 7856909654982853104L;

  private Long id;
  private String name;
  private Integer age;
  private List<Hobby> hobbies = new ArrayList<>();

  public Student(Long id, String name, Integer age) {
   this.id = id;
   this.name = name;
   this.age = age;
  }
 }

 @Data
 @NoArgsConstructor
 @AllArgsConstructor
 private static class Hobby implements Serializable {
  private Long id;
  private String name;
 }
}
