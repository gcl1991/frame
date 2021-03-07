DROP DATABASE IF EXISTS mybatis;
CREATE DATABASE mybatis DEFAULT CHARACTER SET utf8;
USE mybatis;

DROP TABLE IF EXISTS sys_dict;
CREATE TABLE sys_dict(
`id` bigint(32) NOT NULL AUTO_INCREMENT comment '主键',
`code` varchar(64) NOT NULL comment '类别',
`name` varchar(64) NOT NULL comment '字典名',
`value` varchar(64) NOT NULL comment '字典值',
PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
INSERT INTO sys_dict VALUES (1,'性别', '男', '男');
INSERT INTO sys_dict VALUES (2,'性别', '女', '女');
INSERT INTO sys_dict VALUES (3,'季度','第一季度',1);
INSERT INTO sys_dict VALUES (4,'季度','第二季度',2);
INSERT INTO sys_dict VALUES (5,'季度','第三季度',3);
INSERT INTO sys_dict VALUES (6,'季度','第四季度',4);