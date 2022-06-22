create table farmily_member(
	id varchar2(100) primary key,
	password varchar2(100) not null,
	name varchar2(100) not null,
	tel number not null,
	region varchar2(100) not null,
	enabled number default 1
);
insert into farmily_member(id,password,name,tel,region,enabled) values('java','a','감자깡','11','전남',1)
select * from farmily_member where id='java'

create table farmily_authorities(
	authority varchar2(100) primary key,
	id varchar2(100) not null,
	CONSTRAINT fk_farmily_authorities foreign key(id) references farmily_member (id)  --외래키 지정문
  --CONSTRAINT [FK명] foreign key([FK가 될 컬럼명]) references [PK가 위치하는 테이블] ([PK컬럼명])
)

create table farmily_board(
	board_no number primary key,
	title varchar2(100) not null,
	content clob not null,
	select_categori varchar2(100) not null,
	board_categori varchar2(100) not null,
	region varchar2(100) not null,
	created_date date,
	id varchar2(100) not null,
	CONSTRAINT fk_farmily_board foreign key(id) references farmily_member (id)  --외래키 지정문
)

insert into farmily_board(board_no,title,content,select_categori,board_categori,region,created_date,id)
values(seq_farmily_board.nextval,'전남','test','농촌활동','일일알바','전남',sysdate,'java')

insert into farmily_board
values(seq_farmily_board.nextval,'팔아요','가나다','장터','중고거래','전남',sysdate,'java')

select * from farmily_board

create table farmily_file(
	file_no number primary key,
	original_filename varchar2(100) not null,
	saved_filename varchar2(100) not null,
	filepath varchar2(100) not null,
	board_no number not null,
	CONSTRAINT fk_farmily_file foreign key(board_no) references farmily_board(board_no)  --외래키 지정문
)

create table farmily_reservation(
	reservation_no number primary key,
	month varchar2(50) not null,
	day varchar2(50) not null,
	board_no number not null,
	id varchar2(100),
	CONSTRAINT fk_reservation_board_no foreign key(board_no) references farmily_board(board_no),  --외래키 지정문
	CONSTRAINT fk_reservation_id foreign key(id) references farmily_member (id)  --외래키 지정문
)

create table farmily_jjim(
	board_no number ,
	id varchar2(100) ,
	CONSTRAINT pk_jjim PRIMARY KEY(board_no, id),
	CONSTRAINT fk_jjim_board_no foreign key(board_no) references farmily_board(board_no),  --외래키 지정문
	CONSTRAINT fk_jjim_id foreign key(id) references farmily_member (id)  --외래키 지정문
)

create table farmily_chat(
	sender varchar2(100),
	receiver varchar2(100),
	content clob not null,
	chat_date date,
	constraint fk_chat_sender foreign key(sender) references farmily_member(id),
	constraint fk_chat_receiver foreign key(receiver) references farmily_member(id),
	constraint pk_chat primary key(sender, receiver)
)


commit
commit commit
select * from farmily_board

create sequence seq_farmily_board;
create sequence seq_farmily_file;
create sequence seq_farmily_reservation;

alter table farmily_board modify region null;















