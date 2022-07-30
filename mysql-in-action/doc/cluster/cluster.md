# `MySQL` 主从

## 1.挂载目录

```shell
$ mkdir -p /docker-data/mysql/master/conf
$ mkdir -p /docker-data/mysql/slave/conf
```

## 2.`Master` 配置文件

```shell
# master

$ cd /docker-data/mysql/master/conf
$ vim my.cnf
[client]
port=3306
default-character-set=utf8
[mysql]
default-character-set=utf8
[mysqld]
binlog-ignore-db=mysql
character_set_server=utf8
sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES
lower_case_table_names=1
server-id=1
log-bin=mysql-bin
```

## 3.`Slave` 配置文件

```shell
# slave

$ cd /docker-data/mysql/slave/conf
$ vim my.cnf
[client]
port=3306
default-character-set=utf8
[mysql]
binlog-ignore-db=mysql
default-character-set=utf8
[mysqld]
character_set_server=utf8
sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES
lower_case_table_names=1
server-id=2
```

## 4.`docker-compose`

```yaml
## docker-compose.yaml
## $ cd /docker-data/mysql
## $ vim docker-compose.yaml

version: '3'

services:
  mysql-slave:
    image: mysql:5.7
    depends_on:
      - mysql-master
    links:
      - mysql-master
    volumes:
      - /docker-data/mysql/slave/data:/var/lib/mysql
      - /docker-data/mysql/slave/conf:/etc/mysql
      - /docker-data/mysql/slave/log:/var/log/mysql
    ports:
      - "3308:3306"
    restart: always
    hostname: mysql-slave
    environment:
      MYSQL_ROOT_PASSWORD: root
    container_name: mysql-slave
  mysql-master:
    image: mysql:5.7
    volumes:
      - /docker-data/mysql/master/data:/var/lib/mysql
      - /docker-data/mysql/master/conf:/etc/mysql
      - /docker-data/mysql/master/log:/var/log/mysql
    ports:
      - "3307:3306"
    restart: always
    hostname: mysql-master
    environment:
      MYSQL_ROOT_PASSWORD: root
    container_name: mysql-master
```

## 5.容器操作

### 5.1.`Master`

```shell
# -- --------------------------------------- MASTER

$ docker exec -it mysql-master /bin/bash

CREATE USER slave;
GRANT REPLICATION SLAVE ON *.* TO 'slave'@'172.18.0.%' IDENTIFIED BY 'slave';

# -- exit -> unlock
FLUSH TABLES WITH READ LOCK;

SHOW MASTER STATUS;
# -- ---------------------------------------
mysql> SHOW MASTER STATUS;
+------------------+----------+--------------+------------------+-------------------+
| File             | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
+------------------+----------+--------------+------------------+-------------------+
| mysql-bin.000003 |      609 |              |                  |                   |
+------------------+----------+--------------+------------------+-------------------+
1 row in set (0.00 sec)

# -- ---------------------------------------
# -- 查看 变量
SHOW VARIABLES LIKE '%log_bin%';
mysql> SHOW VARIABLES LIKE '%log_bin%';

+---------------------------------+--------------------------------+
| Variable_name                   | Value                          |
+---------------------------------+--------------------------------+
| log_bin                         | ON                             |
| log_bin_basename                | /var/lib/mysql/mysql-bin       |
| log_bin_index                   | /var/lib/mysql/mysql-bin.index |
| log_bin_trust_function_creators | OFF                            |
| log_bin_use_v1_row_events       | OFF                            |
| sql_log_bin                     | ON                             |
+---------------------------------+--------------------------------+
6 rows in set (0.00 sec)
# -- log_bin --> ON --> 开启了 binlog
```

### 5.2.`Slave`

```shell
# docker exec -it mysql-slave /bin/bash

CHANGE MASTER TO MASTER_HOST='mysql-master',
MASTER_PORT=3306,
MASTER_USER='slave',
MASTER_PASSWORD='slave',
MASTER_LOG_FILE='mysql-bin.000003',
MASTER_LOG_POS=10288;
# -- 启动同步进程
START SLAVE;
# -- 查看进程
SHOW SLAVE STATUS;
```

