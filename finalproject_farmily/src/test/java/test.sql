create table farmily_member(
	id varchar2(100) primary key,
	password varchar2(100) not null,
	name varchar2(100) not null,
	tel number not null,
	region varchar2(100) not null,
	enabled number default 1
);

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
	CONSTRAINT fk_farmily_reservation_board_no foreign key(board_no) references farmily_board(board_no)  --외래키 지정문
	CONSTRAINT fk_farmily_reservation_id foreign key(id) references farmily_member (id)  --외래키 지정문
)

create table farmily_jjim(
	board_no number ,
	id varchar2(100) ,
	CONSTRAINT pk_farmily_jjim PRIMARY KEY(board_no, id)
	CONSTRAINT fk_farmily_jjim_board_no foreign key(board_no) references farmily_board(board_no)  --외래키 지정문
	CONSTRAINT fk_farmily_jjim_id foreign key(id) references farmily_member (id)  --외래키 지정문
)

create table farmily_chat(
	sender varchar2(100),
	receiver varchar2(100),
	content clob not null,
	chat_date date
	constraint fk_farmily_chat_sender foreign key(sender) references farmily_member(id),
	constraint fk_farmily_chat_receiver foreign key(receiver) references farmily_member(id),
	constraint pk_farmily_chat primary key(sender, receiver)
)


commit
commit commit
select * from farmily_board

















