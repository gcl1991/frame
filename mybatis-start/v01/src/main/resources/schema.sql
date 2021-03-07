use mybatis;
drop table if exists country; 
create table country(
	id int not null auto_increment,
    countryname varchar(255),
    countrycode varchar(255),
    primary key(id)
);
insert into country(countryname,countrycode) values
('中国','CN'),
('美国','US'),
('俄罗斯','RU'),
('英国','GB'),
('法国','RF');
