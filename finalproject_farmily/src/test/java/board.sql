--farmily_file 테이블 drop하고 다시 생성
drop table farmily_file
create table farmily_file(
	id number primary key,
	title varchar2(100) not null,
	content varchar2(300) not null,
	filename varchar2(150) not null,
	filepath varchar2(300) not null,
	board_no number not null,
	CONSTRAINT fk_farmily_file foreign key(board_no) references farmily_board(board_no)  --외래키 지정문
)


select * from farmily_board 
--BOARD_NO, TITLE, CONTENT, SELECT_CATEGORI, BOARD_CATEGORI, REGION, CREATED_DATE, ID

select * from farmily_member 
--ID, PASSWORD, NAME, TEL, REGION, ENABLED

select * from farmily_file 
--FILE_NO, ORIGINAL_FILENAME, SAVED_FILENAME, FILEPATH, BOARD_NO


--DELETE FROM emp03 WHERE emp_id = 4;
DELETE FROM FARMILY_BOARD WHERE BOARD_NO = 2

--update spring_member set password='b',name='아이유2',address='종로' where id='java';
--update spring_member set password=#{password},name=#{name},address=#{address} where id=#{id}
update SPRING_BOARD SET TITLE=#{TITLE}, CONTENT=#{CONTENT}, SELECT_CATEGORI=#{SELECT_CATEGORI}, BOARD_CATEGORI=#{BOARD_CATEGORI} REGION=#{REGION} 

INSERT INTO farmily_member(ID, PASSWORD, NAME, TEL, REGION, ENABLED)
VALUES ('ZOO','1234','주현','010','서울','1')

--게시글에 글 몇 개 넣어두기
INSERT INTO FARMILY_BOARD(BOARD_NO, TITLE, CONTENT, SELECT_CATEGORI, BOARD_CATEGORI, REGION, CREATED_DATE, ID)
VALUES (seq_farmily_board.nextval,'첫번째 글','안녕하세요오~','자유게시판','농촌활동','서울',sysdate,'ZOO');
INSERT INTO FARMILY_BOARD(BOARD_NO, TITLE, CONTENT, SELECT_CATEGORI, BOARD_CATEGORI, REGION, CREATED_DATE, ID)
VALUES (seq_farmily_board.nextval,'두번째 글','귀농 꿈나무입니다','자유게시판','농촌활동','서울',sysdate,'ZOO');
INSERT INTO FARMILY_BOARD(BOARD_NO, TITLE, CONTENT, SELECT_CATEGORI, BOARD_CATEGORI, REGION, CREATED_DATE, ID)
VALUES (seq_farmily_board.nextval,'세번째 글','많이 배우겠습니다','자유게시판','농촌활동','서울',sysdate,'ZOO');
INSERT INTO FARMILY_BOARD(BOARD_NO, TITLE, CONTENT, SELECT_CATEGORI, BOARD_CATEGORI, REGION, CREATED_DATE, ID)
VALUES (seq_farmily_board.nextval,'네번째 글','농촌에서 일해보고 싶어요','자유게시판','농촌활동','서울',sysdate,'ZOO');
INSERT INTO FARMILY_BOARD(BOARD_NO, TITLE, CONTENT, SELECT_CATEGORI, BOARD_CATEGORI, REGION, CREATED_DATE, ID)
VALUES (seq_farmily_board.nextval,'네번째 글','농촌에서 일해보고 싶어요','자유게시판','농촌활동','서울',sysdate,'ZOO');
