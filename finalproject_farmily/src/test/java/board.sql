--pagination
select rnum, board_no,title,content,select_categori,board_categori,region,created_date,id,detail_region,hit
		from(select row_number() over(order by board_no desc) as rnum, board_no,title,content,select_categori,board_categori,region,created_date,id,detail_region,hit 
		from farmily_board)
		where rnum between 1 and 5

select * from farmily_board
select * from FARMILY_MEMBER
--profile photo를 위한 filename, filepath
ALTER TABLE farmily_MEMBER ADD filename varchar2(150);
ALTER TABLE farmily_MEMBER ADD filepath varchar2(300);







--farmily_file 테이블 drop하고 다시 생성
drop table farmily_file
create table farmily_file(
	filename varchar2(150) primary key,
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

--글 상세보기
select b.board_no, b.title, b.content, b.created_date, b.id, f.filename, f.filepath 
from farmily_board b
inner join farmily_file f on b.id=f.id
where b.board_no=47
		
select * from farmily_file
INSERT INTO FARMILY_file(ID,TITLE,CONTENT,FILENAME,FILEPATH,BOARD_NO)
VALUES ('hy67800','title','content',02615,00654,47);

--paging
select rnum, board_no,title,content,select_categori,board_categori,region,created_date,id 
from(select ROW_NUMBER() OVER(ORDER BY board_no DESC) as rnum, board_no,title,content,select_categori,board_categori,region,created_date,id 
from farmily_board
where board_categori = '장터' order by board_no desc
)
WHERE rnum between 1 and 5