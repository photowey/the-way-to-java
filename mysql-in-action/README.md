# `MySQL`

## 1.实战示例

[student.sql](./doc/student.sql)



### 1.1.实现虚拟列分组递增

```sql
SELECT
	t1.id,
	( @rowNo := CASE WHEN @studentCode = t1.`student_code` THEN @rowNo + 0 ELSE @rowNo + 1 END ) AS studentNo,
	( @studentCode := t1.`student_code` ) AS studentCode,
	t1.`student_name` AS studentName 
FROM
	`student` t1,
	( SELECT @rowNo := 0, @studentCode := '' ) t2 
ORDER BY
	t1.`student_code`;
```



### 1.2.查询重复的数据

> 排除 ` id` 最小的一条

#### 1.2.1.方案一

```sql
SELECT
	*
FROM
	student s 
WHERE
	s.student_code IN ( SELECT student_code FROM `student` t1 GROUP BY t1.student_code HAVING COUNT( * ) > 1 ) 
	AND s.id NOT IN ( SELECT MIN( id ) FROM `student` t2 GROUP BY t2.student_code HAVING COUNT( * ) > 1 );
```



#### 1.2.2.方案二

```sql
SELECT
	*
FROM
	student s 
WHERE
	s.id NOT IN ( SELECT temp_table.id FROM ( SELECT MIN( id ) AS id FROM student t1 GROUP BY t1.student_code ) temp_table );
```



#### 1.2.3.方案三

```sql
SELECT
	*
FROM
	student s 
WHERE s.id <> ( SELECT MIN(t1.id) FROM student t1 WHERE s.student_code = t1.student_code);
```



### 1.3.删除重复数据

> 排除 ` id` 最小的一条



#### 1.3.1.方案一

```sql
DELETE 
FROM
	student 
WHERE
	id IN (
SELECT
	temp_table.id 
FROM
	(
SELECT
	s.id AS id 
FROM
	student s 
WHERE
	s.student_code IN ( SELECT student_code FROM `student` t1 GROUP BY t1.student_code HAVING COUNT( * ) > 1 ) 
	AND s.id NOT IN ( SELECT MIN( id ) FROM `student` t2 GROUP BY t2.student_code HAVING COUNT( * ) > 1 ) 
	) temp_table 
	);
```



#### 1.3.2.方案二

```sql
DELETE 
FROM
	student 
WHERE
	id IN (
SELECT
	temp_table.id 
FROM
	(
SELECT
	s.id AS id 
FROM
	student s 
WHERE
	s.id NOT IN ( SELECT temp_table.id FROM ( SELECT MIN( t1.id ) AS id FROM student t1 GROUP BY t1.student_code ) temp_table ) 
	) temp_table 
	);
```



### 1.3.3.方案三

```sql
DELETE 
FROM
	student 
WHERE
	id IN (
SELECT
	temp_table.id 
FROM
	(
SELECT
	s.id AS id 
FROM
	student s 
WHERE
	s.id <> ( SELECT MIN( t1.id ) FROM student t1 WHERE s.student_code = t1.student_code ) 
	) temp_table 
	);
```

