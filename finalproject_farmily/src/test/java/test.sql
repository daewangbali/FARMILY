create table member(
	id varchar2(100) primary key,
	name varchar2(100) not null,
	tel number not null,
	password varchar2(100) not null,
	region varchar2(100) not null,
	enabled number default 1;
	
)