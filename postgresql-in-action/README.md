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



### 5.2.时间函数

> - `AGE(timestamp,timestamp)`
>   - `AGE(timestamp)` -- 当前时间作为-第一个参数
> - `CURRENT_DATE`
>   - 当前日期的值
> - `CURRENT_TIME`
>   - 返回带时区的当前时间
> - `CURRENT_TIMESTAMP`
>   - 带有时区的当前日期和时间，即事务开始的时间
> - `DATE_PART`
> - `LOCALTIME`
> - `LOCALTIMESTAMP`
>   - 不带时区
> - `EXTRACT`
> - `TO_DATE`
>   - 指定格式将字符串转换为有效日期
> - `TO_TIMESTAMP`
>   - 指定格式将字符串转换为时间戳
> - `NOW`
>   - 返回类型是带时区的 `timestamp`
> - `DATE_TRUNC`

```sql
SELECT current_date, AGE(timestamp '2000-01-01')
```

```sql
SELECT CURRENT_DATE;
```

```sql
SELECT CURRENT_TIME(6);

-- precisio n参数指定返回的小数秒精度。
-- 如果你不传入 precision 参数，结果将包括完全可用的精度。
```



```sql
SELECT CURRENT_TIMESTAMP(2);
-- 2022-11-11 13:04:13.96 +00:00
```



```sql
CREATE TABLE rank(
    rank_id serial PRIMARY KEY,
    rank_name varchar(255) NOT NULL,
    create_time TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
```



`DATE_PART`

```sql
-- century
SELECT DATE_PART('century',TIMESTAMP '2022-11-11');

-- year
SELECT DATE_PART('year',TIMESTAMP '2022-11-11');

-- quarter
SELECT DATE_PART('quarter',TIMESTAMP '2022-11-11');

-- month
SELECT DATE_PART('month',TIMESTAMP '2022-11-11');

-- week
SELECT DATE_PART('week',TIMESTAMP '2022-11-11');

-- day
SELECT DATE_PART('day',TIMESTAMP '2022-11-11 11:11:11');

SELECT DATE_PART('hour',TIMESTAMP '2022-11-11 11:11:11') h,
       DATE_PART('minute',TIMESTAMP '2022-11-11 11:11:11') m,
       DATE_PART('second',TIMESTAMP '2022-11-11 11:11:11') s;
       
SELECT DATE_PART('dow',TIMESTAMP '2022-11-11 11:11:11') dow,
       DATE_PART('doy',TIMESTAMP '2022-11-11 11:11:11') doy;
```



`EXTRACT`

```sql
SELECT EXTRACT(YEAR FROM TIMESTAMP '2022-11-11 11:11:11');

SELECT EXTRACT(QUARTER FROM TIMESTAMP '2022-11-11 11:11:11');

SELECT EXTRACT(MONTH FROM TIMESTAMP '2022-11-11 11:11:11');

SELECT EXTRACT(DAY FROM TIMESTAMP '2022-11-11 11:11:11');

SELECT EXTRACT(CENTURY FROM TIMESTAMP '2022-11-11 11:11:11');

SELECT EXTRACT(DOW FROM TIMESTAMP '2022-11-11 11:11:11');

SELECT EXTRACT(DOY FROM TIMESTAMP '2022-11-11 11:11:11');

SELECT EXTRACT(HOUR FROM TIMESTAMP '2022-11-11 11:11:11');

SELECT EXTRACT(MINUTE FROM TIMESTAMP '2022-11-11 11:11:11');

SELECT EXTRACT(SECOND FROM TIMESTAMP '2022-11-11 11:11:11');

SELECT EXTRACT(YEAR FROM INTERVAL '6 years 5 months 4 days 3 hours 2 minutes 1 second' );
```



`TO_DATE`

```sql
SELECT TO_DATE('20221111','YYYYMMDD');

-- YYYY: 四位数格式的年份
-- MM: 两位数格式的月份
-- DD:两位数格式的天
```



`TO_TIMESTAMP`

```sql
SELECT TO_TIMESTAMP('2022-11-11 11:11:11', 'YYYY-MM-DD HH:MI:SS');
```



```sql
SELECT TO_TIMESTAMP('2022     Oct','YYYY MON');

-- ERROR
SELECT TO_TIMESTAMP('2022     Oct','FXYYYY MON');

SELECT 
    TO_TIMESTAMP('2022-13-32 48:6:66', 'YYYY-MM-DD HH24:MI:SS');
```



`DATE_TRUNC`

```sql
SELECT DATE_TRUNC('hour', TIMESTAMP '2022-11-11 11:11:11');
SELECT DATE_TRUNC('minute', TIMESTAMP '2022-11-11 11:11:11');
```



### 5.3.特殊函数

```sql
CREATE TABLE pro_rank
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    team CHAR(4)     NOT NULL,
    line VARCHAR(20) NOT NULL,
    rank INT         NOT NULL
);

INSERT INTO pro_rank(name, team, line, rank)
VALUES ('369', 'JDG', 'Top', 3699),
       ('Kanavi', 'JDG', 'Jug', 3700),
       ('Yagao', 'JDG', 'Mid', 3666),
       ('Hope', 'JDG', 'Adc', 2222),
       ('Missing', 'JDG', 'Sup', 3333),
       ('Bin', 'BLG', 'Top', 3500),
       ('Ning', 'NULL', 'Jug', 0),
       ('Cream', 'OMG', 'Mid', 2200),
       ('Light', 'LNG', 'Adc', 2500),
       ('Hang', 'WBG', 'Sup', -2000);
```

```sql
SELECT id,
       name,
       rank,
       DENSE_RANK() OVER (
           ORDER BY rank DESC
           ) pro_rank
FROM pro_rank;

SELECT name,
       team,
       rank,
       CUME_DIST() OVER (
           PARTITION BY team
           ORDER BY rank
           )
FROM pro_rank;

SELECT name,
       team,
       rank,
       CUME_DIST() OVER (
           ORDER BY rank
           )
FROM pro_rank
WHERE team = 'JDG';

SELECT id,
       name,
       team,
       rank,
       DENSE_RANK() OVER (
           PARTITION BY team
           ORDER BY rank DESC
           ) pro_rank
FROM pro_rank;


WITH cte AS (SELECT id,
                    name,
                    team,
                    rank,
                    DENSE_RANK() OVER (
                        PARTITION BY team
                        ORDER BY rank DESC
                        ) pro_rank
             FROM pro_rank)
SELECT id,
       name,
       rank
FROM cte
WHERE pro_rank = 1;

SELECT id,
       name,
       team,
       rank,
       FIRST_VALUE(name)
       OVER (
           ORDER BY rank
           ) lowest_pro
FROM pro_rank;

SELECT id,
       name,
       team,
       rank,
       FIRST_VALUE(name)
       OVER (
           PARTITION BY team
           ORDER BY rank
           RANGE BETWEEN
               UNBOUNDED PRECEDING AND
               UNBOUNDED FOLLOWING
           ) team_lowest_pro
FROM pro_rank;

WITH cte AS (SELECT team,
                    SUM(rank) rankx
             FROM pro_rank
             GROUP BY team)
SELECT team,
       rankx,
       LAG(rankx, 1) OVER (
           ORDER BY team
           ) team_pro_rank
FROM cte;


WITH cte AS (SELECT team,
                    SUM(rank) rankall
             FROM pro_rank
             GROUP BY team),
     cte2 AS (SELECT team,
                     rankall,
                     LAG(rankall, 1) OVER (
                         ORDER BY team
                         ) team_pro_teams
              FROM cte)
SELECT team,
       rankall,
       team_pro_teams,
       (team_pro_teams - rankall) variance
FROM cte2;


SELECT id,
       name,
       rank,
       LAST_VALUE(name)
       OVER (
           ORDER BY rank
           RANGE BETWEEN
               UNBOUNDED PRECEDING AND
               UNBOUNDED FOLLOWING
           ) highest_rank
FROM pro_rank;

SELECT id,
       name,
       team,
       rank,
       LAST_VALUE(name)
       OVER (
           PARTITION BY team
           ORDER BY rank
           RANGE BETWEEN
               UNBOUNDED PRECEDING AND
               UNBOUNDED FOLLOWING
           ) highest_rank
FROM pro_rank;

```





## 6.`Schema`

### 6.1.创建 `schema`

```sql
CREATE SCHEMA [IF NOT EXISTS] schema_name;
-- CREATE ROLE xx LOGIN PASSWORD 'pwd';
-- CREATE SCHEMA AUTHORIZATION xx;

CREATE SCHEMA [IF NOT EXISTS] schema_name AUTHORIZATION username;
-- CREATE SCHEMA IF NOT EXISTS xx AUTHORIZATION yyy;
```



当前数据库中的所有 `schema`

```sql
SELECT *
FROM pg_catalog.pg_namespace
ORDER BY nspname;
```



### 6.2.修改 `schema`

> `ALTER SCHEMA` 语句允许我们更改 `schema` 的定义

```sql
ALTER SCHEMA schema_name RENAME TO new_name;
-- 执行此语句，我们必须是schema的所有者，并且必须具有CREATE数据库特权。

ALTER SCHEMA schema_name 
OWNER TO { new_owner | CURRENT_USER | SESSION_USER};
```



查询用户创建的 `schema` 的语句

```sql
SELECT *
FROM pg_catalog.pg_namespace
WHERE nspacl is NULL
  AND nspname NOT LIKE 'pg_%'
ORDER BY nspname;
```



### 6.3.删除 `schema`

> `DROP SCHEMA` 移除一个 `SCHEMA` 以及数据库中的所有对象

```sql
DROP SCHEMA [IF EXISTS] schema_name [ CASCADE | RESTRICT ];

DROP SCHEMA [IF EXISTS] schema_name1 [,schema_name2,...] [CASCADE | RESTRICT];
```



## 7.角色 `ROLE`

### 7.1.创建 `ROLE`

```sql
CREATE ROLE role_name;
-- 创建角色时，该角色在数据库服务器(或集群)中的所有数据库中都有效。
-- CREATE ROLE name WITH option;
---- WITH 关键字是可选的。
---- option 可以是一个或多个属性，包括SUPER,CREATEDB,CREATEROLE等等。

-- SELECT rolname FROM pg_roles;
---- 以 pg_ 前缀开头的角色，是系统的角色。
```

#### 创建用户

```sql
CREATE ROLE hello LOGIN  PASSWORD 'hello';
```

#### 创建超级用户

```sql
CREATE ROLE super_user
SUPERUSER 
LOGIN 
PASSWORD 'super_user';

-- 创建超级用户角色
---- 超级用户可以获取数据库中的所有访问限制，因此我们应仅在需要时创建此角色。
---- 必须是超级用户才能创建另一个超级用户角色。
```

#### 数据库创建特权

```sql
CREATE ROLE dba 
CREATEDB 
LOGIN 
PASSWORD 'dba';

-- 创建具有数据库创建特权的角色，请使用CREATEDB属性。
```

#### 有效期的角色

```sql
CREATE ROLE dev WITH
LOGIN
PASSWORD 'dev'
VALID UNTIL '2023-01-01';

-- 创建具有有效期的角色
```



```sql
CREATE ROLE api
LOGIN
PASSWORD 'api123'
CONNECTION LIMIT 1000;
```



### 7.2.`GRANT`

```sql
GRANT privilege_list | ALL 
ON  table_name
TO  role_name;

-- 指定 privilege_list (权限列表：可以是SELECT,INSERT,UPDATE,DELETE,TRUNCATE等等)。
-- 使用 ALL 向角色授予表的所有操作权限。

-- ON 关键字后指定表名。
-- TO 关键字后指定要向其授予权限的角色名。
```



```sql
GRANT ALL
    ON ALL TABLES
    IN SCHEMA "public"
    TO abcd;

-- 向角色授予schema中所有表的所有权限
```



```sql
-- 只读角色
GRANT SELECT
    ON ALL TABLES
    IN SCHEMA "public"
    TO only_reader;
```



### 7.3.`REVOKE`

> `REVOKE` 语句撤销先前从角色授予的数据库对象权限。

```sql
REVOKE privilege_list | ALL
ON TABLE table_name | ALL TABLES IN SCHEMA schema_name
FROM role_name;

-- ON 关键字后指定表。
---- 可以使用ALL TABLES指定从schema中的所有表撤销指定的权限。
-- FROM 关键字后指定要从中撤销权限的角色的名称。
```



### 7.4.角色组

#### 7.4.1.创建角色组

```sql
CREATE ROLE group_role_name;
```



#### 7.4.2.将角色添加到角色组

```sql
GRANT group_role to user_role;
```



#### 7.4.3.从角色组中删除用户角色

```sql
REVOKE group_role FROM user_role;
```



#### 7.4.4.修改角色

```sql
ALTER ROLE role_name [WITH] option;

-- SUPERUSER | NOSUPERUSER – 是否是SUPERUSER 。
-- CREATEDB | NOCREATEDB – 是否允许角色创建新数据库。
-- CREATEROLE | NOCREATEROLE – 是否允许角色创建或更改角色。
-- -- INHERIT | NOINHERIT – 是否确定要继承其所属角色组的角色权限。
-- LOGIN | NOLOGIN – 允许角色登录。
-- REPLICATION | NOREPLICATION – 确定该角色是否为复制角色。
-- BYPASSRLS | NOBYPASSRLS – 确定角色是否通过行级安全 (RLS) 策略。
-- CONNECTION LIMIT limit – 指定角色可以建立的并发连接数，-1表示无限制。
-- PASSWORD 'password' | PASSWORD NULL – 角色的密码。
-- VALID UNTIL 'timestamp' – 设置角色密码的有效期。

-- 超级管理员可以为任何角色更改这些属性中的任何一个。
-- 仅针对非超级用户和无复制角色：角色具有CREATEROLE权限可以更改这些属性中的任何一个。
-- 角色只能更改自己的密码
```



#### 7.4.5. 重命名角色

```sql
ALTER ROLE role_name ALTER TO new_name;
```



#### 7.4.6.更改角色配置变量默认值

```sql
ALTER ROLE role_name | CURRENT_USER | SESSION_USER | ALL
[IN DATABASE database_name]
SET configuration_param = { value | DEFAULT }

-- 超级管理员可以更改任何角色的默认值。
-- 角色拥有CREATEROLE权限可以设置非超级用户角色的默认值。
-- 普通角色只能为自己设置默认值
```



### 7.5.删除角色

> `DROP ROLE`

```sql
DROP ROLE [IF EXISTS] target_role;
```



## 8.备份

> 使用 `pg_dump` 和 `pg_dumpall` 命令备份数据库



### 8.1.单个数据库

```sql
pg_dump -U username -W -F t database_name > /path/to/xxx.tar

-- -F : 指定可以为以下之一的输出文件格式:
---- c: 自定义格式的存档文件格式
---- d: 目录格式存档
---- t: tar
---- p: 纯文本 SQL 脚本文件.
```



### 8.2.所有数据库

```sql
pg_dumpall -U postgres > /path/to/xxx.tar

-- 只想备份角色
pg_dumpall --roles-only > /path/to/xxx.sql

-- 只想备份表空间
pg_dumpall --tablespaces-only > /path/to/xxx.sql
```



### 8.3.还原数据库

> - 使用 `psql` 恢复由 `pg_dump` 和 `pg_dumpall` 命令生成的纯SQL脚本文件。
> - 使用 `pg_restore` 恢复由 `pg_dump` 命令创建的 `tar` 文件和目录格式。



#### 8.3.1.还原完整备份

>  `psql`

```sql
-- 忽略还原过程中发生的任何错误
psql -U username -f backupfile.sql
```

```sql
-- 在出现错误时停止还原数据库
psql -U username --set ON_ERROR_STOP=on -f backupfile.sql
```

```sql
-- 备份特定数据库中的对象
psql -U username -d database_name -f objects.sql
```



>  `pg_restore`

```sql
-- 更换数据库名称
pg_restore --dbname=newdb --verbose /path/to/xxx.tar
```

```sql
-- 还原与备份数据库相同的数据库
pg_restore --dbname=db --create --verbose /path/to/xxx.tar
```

```sql
pg_restore --dbname=testdb --section=pre-data  /path/to/xxx.tar

-- -- section 仅恢复表结构的选项
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

