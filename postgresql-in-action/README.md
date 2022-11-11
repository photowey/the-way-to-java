# `PostgreSQL`

> `docker`

```shell
$ docker pull postgres:14.4

$ docker run -itd \
--name postgres \
--restart always \
--privileged \
-p 5432:5432 \
-e POSTGRES_PASSWORD=postgres \
-v /opt/postgres/data:/var/lib/postgresql/data \
postgres:14.4
```



> @see https://juejin.cn/post/7156408589718913038

## 1.建库

> `CREATE DATABASE`

```sql
CREATE DATABASE database_name
WITH
   [OWNER =  role_name]
   [TEMPLATE = template]
   [ENCODING = encoding]
   [LC_COLLATE = collate]
   [LC_CTYPE = ctype]
   [TABLESPACE = tablespace_name]
   [ALLOW_CONNECTIONS = true | false]
   [CONNECTION LIMIT = max_concurrent_connection]
   [IS_TEMPLATE = true | false ]
   
-- 1.OWNER 所有者
---- 给创建的数据库分配一个角色，这将是数据库的所有者。如果你省略了OWNER选项，数据库的所有者是执行CREATE DATABASE时的角色。

-- 2.TEMPLATE 模板
---- 默认情况下，PostgreSQL使用template指定从中创建新数据库的模板数据库。如果未明确指定模板数据库，则将默认数据库作为模板数据库。

-- 3.ENCODING 字符集编码
---- 确定新数据库中的字符集编码。

-- 4.LC_COLLATE 排序规则
---- 指定排序规则顺序 (LC_COLLATE)，新数据库将使用该排序规则。此参数影响的排序顺序字符串查询包含Order By模板数据库。

-- 5.LC_CTYPE 语言符号及其分类
---- 定新数据库将使用的字符分类。 它影响字符的分类，例如大写, 小写, 和数字. 它默认为模板数据库的LC_CTYPE。

-- 6.TABLESPACE 表空间
---- 指定新数据库TABLESPACE的名称。默认值为模板数据库的表空间。

-- 7.CONNECTION LIMIT 最大连接数
---- 指定到新数据库的最大并发连接。默认值为-1，即无限制。此参数在共享托管环境中非常有用，我们可以在其中配置特定数据库的最大并发连接。

-- 8.ALLOW_CONNECTIONS 是否允许连接
---- 参数allow_connections的数据类型是布尔值。如果是false,我们无法连接到数据库。

-- 9.IS_TEMPLATE 是否为模板
---- 如果IS_TEMPLATE是真的，任何角色的CREATE DATABASE都可以克隆它。如果为false，则只有超级用户或数据库所有者可以克隆它。
```



## 2.更新库

> `ALTER DATABASE`

- 更改数据库的属性

  - ```sql
    ALTER DATABASE $name WITH $option;
     
    -- IS_TEMPLATE
    -- CONNECTION LIMIT
    -- ALLOW_CONNECTIONS
    
    -- 注意，只有超级用户或数据库所有者可以更改这些设置。
    ```

  - 

- 重命名数据库

  - ```sql
    ALTER DATABASE $database_name RENAME TO $new_name;
    
    -- 如果你在当前数据库执行语句，无法重命名当前数据库。因此，我们需要连接到另一个数据库并从该数据库重命名它。
    
    -- 只有具有 CREATEDB 特权的超级用户和数据库所有者才能重命名数据库。
    ```

  - 

- 更改数据库的所有者

  - ```sql
    ALTER DATABASE $database_name
    OWNER TO $new_owner | $current_user | $session_user;
    
    -- 数据库所有者，是新create database拥有角色的直接或间接成员。
    -- 超级管理员。
    ```

  - 

- 更改数据库的默认表空间

  - ```sql
    ALTER DATABASE $database_name 
    SET TABLESPACE $new_tablespace;
    
    -- 该语句将表和索引从旧表空间物理移动到新表空间。
    -- 要设置新的表空间，表空间需要为空，并且与数据库有连接。
    
    -- 注意：超级管理员和数据库所有者可以更改数据库的默认表空间。
    ```

  - 

- 更改数据库运行时配置变量的会话默认值

  - ```sql
    ALTER DATABASE $database_name
    SET $configuration_parameter = value;
    
    -- 注意：只有超级用户或数据库所有者才能更改数据库运行时配置的会话默认值。
    ```

  - ```sql
    -- 创建角色
     CREATE ROLE $principal_role
     LOGIN 
     CREATEDB
     PASSWORD '$principal_role_pwd';
    ```

  - ```sql
     CREATE TABLESPACE $principal_default
     OWNER $owner
     LOCATION '/path/to/principal_table_space';
    ```

  - 



## 3.删除库

> `DROP DATABASE`

```sql
DROP DATABASE [IF EXISTS] $database_name;

-- DROP DATABASE删除指定数据库。
-- 使用 如果存在 防止错误删除不存在的数据库。PostgreSQL将发出通知。

---- DROP DATABASE 永久删除目录条目和数据目录。此操作无法撤消，因此我们必须谨慎使用。

---- 只有超级用户和数据库所有者可以执行DROP DATABASE声明。此外，如果数据库仍然具有活动连接,我们不能执行DROP DATABASE语句。在这种情况下，我们需要断开与数据库的连接并连接到另一个数据库，例如，postgres执行DROP DATABASE声明。
```



```sql
-- 删除具有活动连接的数据库
-- 1.找到活动数据库查询pg_stat_activity视图:
 SELECT *
 FROM pg_stat_activity
 WHERE datname = '$database_name';
 
-- 2.通过发出以下查询来终止活动连接:
 SELECT  pg_terminate_backend ($pid)
 FROM    pg_stat_activity
 WHERE   pg_stat_activity.datname = '$database_name';
 
-- 3.执行DROP DATABASE声明:
 DROP DATABASE $database_name;
```



## 4.数据库复制

### 源数据库

```sql
pg_dump -U postgres -d sourcedb -f sourcedb.sql;
```

### 目标数据库

```sql
CREATE DATABASE targetdb;
psql -U postgres -d targetdb -f sourcedb.sql
```



```sql
pg_dump -C -h local -U localuser sourcedb | psql -h remote -U remoteuser targetdb
```



## 5.函数

### 5.1.获取大小函数

> `pg_relation_size`
>
> - 函数以字节为单位返回特定表的大小
> - 函数仅返回表的大小，不包括索引或其他对象
>
> `pg_size_pretty`
>
> - 函数获取另一个函数的结果，并根据需要使用字节、`KB`、`MB`、`GB`或`TB`对其进行格式化
>
> `pg_total_relation_size`
>
> - 表的总大小
>
> `pg_database_size`
>
> - 获取整个数据库的大小
>
> `pg_indexes_size`
>
> - 获取附加到表的所有索引的总大小
>
> `pg_tablespace_size`
>
> - 获取表空间的大小
>
> `pg_column_size`
>
> - 查找需要存储特定值的空间

```sql
SELECT pg_relation_size('user');

SELECT pg_size_pretty (pg_relation_size('user'));

SELECT pg_size_pretty (pg_total_relation_size ('user'));

SELECT pg_size_pretty (pg_database_size ('user'));

SELECT pg_size_pretty (pg_indexes_size('user'));

SELECT pg_size_pretty (pg_tablespace_size ('pg_default'));

-- 2
SELECT pg_column_size(5::smallint);
-- 4
SELECT pg_column_size(5::int);
-- 8
SELECT pg_column_size(5::bigint);
```

```sql
-- 使用 pg_total_relation_size() 函数查找最大表(包括索引)的大小
SELECT relname AS "relation",
       pg_size_pretty(
               pg_total_relation_size(C.oid)
           )   AS "total_size"
FROM pg_class C
         LEFT JOIN pg_namespace N ON (N.oid = C.relnamespace)
WHERE nspname NOT IN (
                      'pg_catalog',
                      'information_schema'
    )
  AND C.relkind <> 'i'
  AND nspname !~ '^pg_toast'
ORDER BY pg_total_relation_size(C.oid) DESC
LIMIT 5;
```



```sql
-- 要获取当前数据库服务器中每个数据库的大小
SELECT pg_database.datname,
       pg_size_pretty(pg_database_size(pg_database.datname)) AS size
FROM pg_database;
```







-- -



## 2.运维语句

> @see https://juejin.cn/post/7161998606029815816

### 显示正在运行的查询

```sql
SELECT
    pid, age(clock_timestamp(), query_start), usename, query, state
FROM "pg_stat_activity"
WHERE
    state != 'idle' AND query NOT ILIKE '%pg_stat_activity%'
ORDER BY query_start DESC;
```



### 找到活跃数据库

```sql
SELECT 
    *
FROM pg_stat_activity
WHERE datname = '<database_name>';
```



### 终止活跃数据库

```sql
SELECT
    pg_terminate_backend (pid)
FROM	pg_stat_activity
WHERE	pg_stat_activity.datname = '<database_name>';
```



### 正在终止运行的查询

```sql
SELECT pg_cancel_backend(procpid);
```



### Kill空闲查询

```sql
SELECT pg_terminate_backend(procpid);
```



### 查询表索引使用率

```sql
SELECT 
relname, 100 * idx_scan / (seq_scan + idx_scan) percent_of_times_index_used, n_live_tup rows_in_table
FROM pg_stat_user_tables
ORDER BY n_live_tup DESC;
```



### 查询缓存中有多少索引

```sql
SELECT sum(idx_blks_read)                                           AS idx_read,
       sum(idx_blks_hit)                                            AS idx_hit,
       (sum(idx_blks_hit) - sum(idx_blks_read)) / sum(idx_blks_hit) AS ratio
FROM pg_statio_user_indexes;
```



### 查询表索引使用率

```sql
SELECT relname,
       CASE
           WHEN (seq_scan + idx_scan) != 0
               THEN 100.0 * idx_scan / (seq_scan + idx_scan)
           ELSE 0
           END    AS percent_of_times_index_used,
       n_live_tup AS rows_in_table
FROM pg_stat_user_tables
ORDER BY n_live_tup DESC;
```



### 查询检查所有数据库的大小

```sql
SELECT d.datname                            AS Name,
       pg_catalog.pg_get_userbyid(d.datdba) AS Owner,
       CASE
           WHEN pg_catalog.has_database_privilege(d.datname, 'CONNECT')
               THEN pg_catalog.pg_size_pretty(pg_catalog.pg_database_size(d.datname))
           ELSE 'No Access'
           END                              AS SIZE
FROM pg_catalog.pg_database d
ORDER BY CASE
             WHEN pg_catalog.has_database_privilege(d.datname, 'CONNECT')
                 THEN pg_catalog.pg_database_size(d.datname)
             ELSE NULL
             END;
```



### 检查每个表的大小

```sql
SELECT nspname || '.' || relname                     AS "relation",
       pg_size_pretty(pg_total_relation_size(C.oid)) AS "total_size"
FROM pg_class C
         LEFT JOIN pg_namespace N ON (N.oid = C.relnamespace)
WHERE nspname NOT IN ('pg_catalog', 'information_schema')
  AND C.relkind <> 'i'
  AND nspname !~ '^pg_toast'
ORDER BY pg_total_relation_size(C.oid) DESC;
```



### 检查当前持有锁

```sql
SELECT t.relname,
       l.locktype,
       page,
       virtualtransaction,
       pid,
       mode,
       granted
FROM pg_locks l,
     pg_stat_all_tables t
WHERE l.relation = t.relid
ORDER BY relation DESC;
```



### 获取所有表大小

```sql
SELECT schema_name,
       relname,
       pg_size_pretty(table_size) AS size,
       table_size
FROM (SELECT pg_catalog.pg_namespace.nspname           AS schema_name,
             relname,
             pg_relation_size(pg_catalog.pg_class.oid) AS table_size
      FROM pg_catalog.pg_class
               JOIN pg_catalog.pg_namespace ON relnamespace = pg_catalog.pg_namespace.oid) t
WHERE schema_name NOT LIKE 'pg_%'
ORDER BY table_size DESC;
```



### 获取 `schema ` 大小

```sql
SELECT *
FROM (SELECT pg_catalog.pg_namespace.nspname                AS schema_name,
             sum(pg_relation_size(pg_catalog.pg_class.oid)) AS schema_size
      FROM pg_catalog.pg_class
               JOIN pg_catalog.pg_namespace ON relnamespace = pg_catalog.pg_namespace.oid
      group by 1) t
WHERE schema_name NOT LIKE 'pg_%'
ORDER BY schema_size DESC;
```



### 显示未使用的索引

```sql
SELECT relname      AS table_name,
       indexrelname AS index_name,
       idx_scan,
       idx_tup_read,
       idx_tup_fetch,
       pg_size_pretty(pg_relation_size(indexrelname::regclass))
FROM pg_stat_all_indexes
WHERE schemaname = 'public'
  AND idx_scan = 0
  AND idx_tup_read = 0
  AND idx_tup_fetch = 0
ORDER BY pg_relation_size(indexrelname::regclass) DESC;
```



### 终止与当前数据库的所有正在运行的连接

```sql
SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE datname = current_database()
  AND pid <> pg_backend_pid();
```



-- -



