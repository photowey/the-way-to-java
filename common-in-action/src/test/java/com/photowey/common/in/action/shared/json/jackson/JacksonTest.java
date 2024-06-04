package com.photowey.common.in.action.shared.json.jackson;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * {@code JSONTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/05/01
 */
class JacksonTest {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Student implements Serializable {

        private static final long serialVersionUID = -9189460711272727208L;

        @JsonView(View.Public.class)
        private Long id;
        @JsonView(View.Public.class)
        private String name;

        private Integer age;
    }

    @Test
    void testJackson_toJSONString() {
        Long now = 1714314630000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        String json = Jackson.toJSONString(student);
        DocumentContext ctx = JsonPath.parse(json);

        Assertions.assertEquals(student.getId(), ctx.read("$.id"));
        Assertions.assertEquals(student.getName(), ctx.read("$.name"));
        Assertions.assertEquals(student.getAge(), ctx.read("$.age"));
    }

    @Test
    void testJackson_toJSONString_with_view() {
        Long now = 1714314630000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        String json = Jackson.toJSONString(student, View.Public.class);
        DocumentContext ctx = JsonPath.parse(json);

        Assertions.assertEquals(student.getId(), ctx.read("$.id"));
        Assertions.assertEquals(student.getName(), ctx.read("$.name"));

        Assertions.assertThrows(PathNotFoundException.class, () -> ctx.read("$.age"));
    }

    @Test
    void testJackson_toPrettyString() {
        Long now = 1714314630000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        String json = Jackson.toJSONString(student);
        Assertions.assertFalse(json.contains("\n"));

        String prettyJson = Jackson.toPrettyString(json);
        Assertions.assertTrue(prettyJson.contains("\n"));
    }

    @Test
    void testParseArray() {
        Long now = 1714497259000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        List<Student> students = new ArrayList<>();
        students.add(student);

        String json = Jackson.toJSONString(students);
        List<Student> peers = Jackson.parseArray(json, new TypeReference<List<Student>>() {});

        Assertions.assertNotNull(peers);
        Assertions.assertEquals(1, peers.size());

        Student peer = peers.get(0);
        Assertions.assertEquals(peer.getId(), student.getId());
        Assertions.assertEquals(peer.getName(), student.getName());
        Assertions.assertEquals(peer.getAge(), student.getAge());
    }

    @Test
    void testParseArray_bytes() {
        Long now = 1714497259000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        List<Student> students = new ArrayList<>();
        students.add(student);

        String json = Jackson.toJSONString(students);
        List<Student> peers = Jackson.parseArray(json.getBytes(StandardCharsets.UTF_8), new TypeReference<List<Student>>() {});

        Assertions.assertNotNull(peers);
        Assertions.assertEquals(1, peers.size());

        Student peer = peers.get(0);
        Assertions.assertEquals(peer.getId(), student.getId());
        Assertions.assertEquals(peer.getName(), student.getName());
        Assertions.assertEquals(peer.getAge(), student.getAge());
    }

    @Test
    void testParseArray_input_stream() {
        Long now = 1714497259000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        List<Student> students = new ArrayList<>();
        students.add(student);

        String json = Jackson.toJSONString(students);
        InputStream input = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        List<Student> peers = Jackson.parseArray(input, new TypeReference<List<Student>>() {});

        Assertions.assertNotNull(peers);
        Assertions.assertEquals(1, peers.size());

        Student peer = peers.get(0);
        Assertions.assertEquals(peer.getId(), student.getId());
        Assertions.assertEquals(peer.getName(), student.getName());
        Assertions.assertEquals(peer.getAge(), student.getAge());
    }

    @Test
    void testToList() {
        Long now = 1714497259000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        List<Student> students = new ArrayList<>();
        students.add(student);

        String json = Jackson.toJSONString(students);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        InputStream input = new ByteArrayInputStream(bytes);

        // ----------------------------------------------------------------

        List<Student> peers1 = Jackson.toList(json, new TypeReference<List<Student>>() {});

        Assertions.assertNotNull(peers1);
        Assertions.assertEquals(1, peers1.size());

        Student peer1 = peers1.get(0);
        Assertions.assertEquals(peer1.getId(), student.getId());
        Assertions.assertEquals(peer1.getName(), student.getName());
        Assertions.assertEquals(peer1.getAge(), student.getAge());

        // ----------------------------------------------------------------

        List<Student> peers2 = Jackson.toList(bytes, new TypeReference<List<Student>>() {});
        Assertions.assertNotNull(peers2);
        Assertions.assertEquals(1, peers2.size());

        Student peer2 = peers2.get(0);
        Assertions.assertEquals(peer2.getId(), student.getId());
        Assertions.assertEquals(peer2.getName(), student.getName());
        Assertions.assertEquals(peer2.getAge(), student.getAge());

        // ----------------------------------------------------------------

        List<Student> peers3 = Jackson.toList(input, new TypeReference<List<Student>>() {});
        Assertions.assertNotNull(peers3);
        Assertions.assertEquals(1, peers3.size());

        Student peer3 = peers3.get(0);
        Assertions.assertEquals(peer3.getId(), student.getId());
        Assertions.assertEquals(peer3.getName(), student.getName());
        Assertions.assertEquals(peer3.getAge(), student.getAge());
    }

    @Test
    void testToSet() {
        Long now = 1714497259000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        List<Student> students = new ArrayList<>();
        students.add(student);

        String json = Jackson.toJSONString(students);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        InputStream input = new ByteArrayInputStream(bytes);

        // ----------------------------------------------------------------

        Set<Student> peers1 = Jackson.toSet(json, new TypeReference<Set<Student>>() {});

        Assertions.assertNotNull(peers1);
        Assertions.assertEquals(1, peers1.size());

        Student peer1 = new ArrayList<>(peers1).get(0);
        Assertions.assertEquals(peer1.getId(), student.getId());
        Assertions.assertEquals(peer1.getName(), student.getName());
        Assertions.assertEquals(peer1.getAge(), student.getAge());

        // ----------------------------------------------------------------

        Set<Student> peers2 = Jackson.toSet(bytes, new TypeReference<Set<Student>>() {});
        Assertions.assertNotNull(peers2);
        Assertions.assertEquals(1, peers2.size());

        Student peer2 = new ArrayList<>(peers2).get(0);
        Assertions.assertEquals(peer2.getId(), student.getId());
        Assertions.assertEquals(peer2.getName(), student.getName());
        Assertions.assertEquals(peer2.getAge(), student.getAge());

        // ----------------------------------------------------------------

        Set<Student> peers3 = Jackson.toSet(input, new TypeReference<Set<Student>>() {});
        Assertions.assertNotNull(peers3);
        Assertions.assertEquals(1, peers3.size());

        Student peer3 = new ArrayList<>(peers3).get(0);
        Assertions.assertEquals(peer3.getId(), student.getId());
        Assertions.assertEquals(peer3.getName(), student.getName());
        Assertions.assertEquals(peer3.getAge(), student.getAge());
    }

    @Test
    void testToCollection() {
        Long now = 1714497259000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        List<Student> students = new ArrayList<>();
        students.add(student);

        String json = Jackson.toJSONString(students);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        InputStream input = new ByteArrayInputStream(bytes);

        // ----------------------------------------------------------------

        Collection<Student> peers1 = Jackson.toCollection(json, new TypeReference<Collection<Student>>() {});

        Assertions.assertNotNull(peers1);
        Assertions.assertEquals(1, peers1.size());

        Student peer1 = new ArrayList<>(peers1).get(0);
        Assertions.assertEquals(peer1.getId(), student.getId());
        Assertions.assertEquals(peer1.getName(), student.getName());
        Assertions.assertEquals(peer1.getAge(), student.getAge());

        // ----------------------------------------------------------------

        Collection<Student> peers2 = Jackson.toCollection(bytes, new TypeReference<Collection<Student>>() {});
        Assertions.assertNotNull(peers2);
        Assertions.assertEquals(1, peers2.size());

        Student peer2 = new ArrayList<>(peers2).get(0);
        Assertions.assertEquals(peer2.getId(), student.getId());
        Assertions.assertEquals(peer2.getName(), student.getName());
        Assertions.assertEquals(peer2.getAge(), student.getAge());

        // ----------------------------------------------------------------

        Collection<Student> peers3 = Jackson.toCollection(input, new TypeReference<Collection<Student>>() {});
        Assertions.assertNotNull(peers3);
        Assertions.assertEquals(1, peers3.size());

        Student peer3 = new ArrayList<>(peers3).get(0);
        Assertions.assertEquals(peer3.getId(), student.getId());
        Assertions.assertEquals(peer3.getName(), student.getName());
        Assertions.assertEquals(peer3.getAge(), student.getAge());
    }

    @Test
    void testToMap() {
        Long now = 1714497259000L;
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        String json = Jackson.toJSONString(student);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        InputStream input = new ByteArrayInputStream(bytes);

        // ----------------------------------------------------------------

        Map<String, Object> peers1 = Jackson.toMap(json, new TypeReference<Map<String, Object>>() {});

        Assertions.assertNotNull(peers1);
        Assertions.assertEquals(3, peers1.size());

        Assertions.assertEquals(peers1.get("id"), student.getId());
        Assertions.assertEquals(peers1.get("name"), student.getName());
        Assertions.assertEquals(peers1.get("age"), student.getAge());

        // ----------------------------------------------------------------

        Map<String, Object> peers2 = Jackson.toMap(bytes, new TypeReference<Map<String, Object>>() {});

        Assertions.assertNotNull(peers2);
        Assertions.assertEquals(3, peers2.size());

        Assertions.assertEquals(peers2.get("id"), student.getId());
        Assertions.assertEquals(peers2.get("name"), student.getName());
        Assertions.assertEquals(peers2.get("age"), student.getAge());

        // ----------------------------------------------------------------

        Map<String, Object> peers3 = Jackson.toMap(input, new TypeReference<Map<String, Object>>() {});

        Assertions.assertNotNull(peers3);
        Assertions.assertEquals(3, peers3.size());

        Assertions.assertEquals(peers3.get("id"), student.getId());
        Assertions.assertEquals(peers3.get("name"), student.getName());
        Assertions.assertEquals(peers3.get("age"), student.getAge());
    }

    // ----------------------------------------------------------------

    @Test
    void testParseArray_simple() {
        long now = System.currentTimeMillis();
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        List<Student> students = new ArrayList<>();
        students.add(student);
        String jsonArray = Jackson.toJSONString(students);
        byte[] bytes = jsonArray.getBytes(StandardCharsets.UTF_8);

        List<Student> simpleList1 = Jackson.parseArray(jsonArray, Student.class);
        Assertions.assertNotNull(simpleList1);
        Assertions.assertEquals(1, simpleList1.size());
        Assertions.assertEquals(now, simpleList1.get(0).getId());

        List<Student> simpleList2 = Jackson.parseArray(bytes, Student.class);
        Assertions.assertNotNull(simpleList2);
        Assertions.assertEquals(1, simpleList2.size());
        Assertions.assertEquals(now, simpleList2.get(0).getId());

        List<Student> simpleList3 = Jackson.parseArray(new ByteArrayInputStream(bytes), Student.class);
        Assertions.assertNotNull(simpleList3);
        Assertions.assertEquals(1, simpleList3.size());
        Assertions.assertEquals(now, simpleList3.get(0).getId());
    }

    @Test
    void testToList_simple() {
        long now = System.currentTimeMillis();
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        List<Student> students = new ArrayList<>();
        students.add(student);
        String jsonArray = Jackson.toJSONString(students);
        byte[] bytes = jsonArray.getBytes(StandardCharsets.UTF_8);

        List<Student> simpleList1 = Jackson.toList(jsonArray, Student.class);
        Assertions.assertNotNull(simpleList1);
        Assertions.assertEquals(1, simpleList1.size());
        Assertions.assertEquals(now, simpleList1.get(0).getId());

        List<Student> simpleList2 = Jackson.toList(bytes, Student.class);
        Assertions.assertNotNull(simpleList2);
        Assertions.assertEquals(1, simpleList2.size());
        Assertions.assertEquals(now, simpleList2.get(0).getId());

        List<Student> simpleList3 = Jackson.toList(new ByteArrayInputStream(bytes), Student.class);
        Assertions.assertNotNull(simpleList3);
        Assertions.assertEquals(1, simpleList3.size());
        Assertions.assertEquals(now, simpleList3.get(0).getId());
    }

    @Test
    void testToSet_simple() {
        long now = System.currentTimeMillis();
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        List<Student> students = new ArrayList<>();
        students.add(student);
        String jsonArray = Jackson.toJSONString(students);
        byte[] bytes = jsonArray.getBytes(StandardCharsets.UTF_8);

        Set<Student> simpleSet1 = Jackson.toSet(jsonArray, Student.class);
        Assertions.assertNotNull(simpleSet1);
        Assertions.assertEquals(1, simpleSet1.size());
        Assertions.assertEquals(now, new ArrayList<>(simpleSet1).get(0).getId());

        Set<Student> simpleSet2 = Jackson.toSet(bytes, Student.class);
        Assertions.assertNotNull(simpleSet2);
        Assertions.assertEquals(1, simpleSet2.size());
        Assertions.assertEquals(now, new ArrayList<>(simpleSet2).get(0).getId());

        Set<Student> simpleSet3 = Jackson.toSet(new ByteArrayInputStream(bytes), Student.class);
        Assertions.assertNotNull(simpleSet3);
        Assertions.assertEquals(1, simpleSet3.size());
        Assertions.assertEquals(now, new ArrayList<>(simpleSet3).get(0).getId());
    }

    @Test
    void testToCollection_simple() {
        long now = System.currentTimeMillis();
        Student student = Student.builder()
                .id(now)
                .name("photowey")
                .age(18)
                .build();

        List<Student> students = new ArrayList<>();
        students.add(student);
        String jsonArray = Jackson.toJSONString(students);
        byte[] bytes = jsonArray.getBytes(StandardCharsets.UTF_8);

        Collection<Student> simpleCollection1 = Jackson.toCollection(jsonArray, Student.class);
        Assertions.assertNotNull(simpleCollection1);
        Assertions.assertEquals(1, simpleCollection1.size());
        Assertions.assertEquals(now, new ArrayList<>(simpleCollection1).get(0).getId());

        Collection<Student> simpleCollection2 = Jackson.toCollection(bytes, Student.class);
        Assertions.assertNotNull(simpleCollection2);
        Assertions.assertEquals(1, simpleCollection2.size());
        Assertions.assertEquals(now, new ArrayList<>(simpleCollection2).get(0).getId());

        Collection<Student> simpleCollection3 = Jackson.toCollection(new ByteArrayInputStream(bytes), Student.class);
        Assertions.assertNotNull(simpleCollection3);
        Assertions.assertEquals(1, simpleCollection3.size());
        Assertions.assertEquals(now, new ArrayList<>(simpleCollection3).get(0).getId());
    }
}