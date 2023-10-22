
-- MySQL
CREATE TABLE `shedlock` (
  `name` varchar(64) NOT NULL,
  `lock_until` timestamp(3) NULL DEFAULT NULL,
  `locked_at` timestamp(3) NULL DEFAULT NULL,
  `locked_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- PostgreSQL
--drop table if exist shedlock;
--create table shedlock (
--   name                 VARCHAR(64)          not null,
--   lock_until           TIMESTAMP            null,
--   locked_at            TIMESTAMP            null,
--   locked_by            VARCHAR(255)         null,
--   constraint PK_SHEDLOCK primary key (name)
--);
