package org.kosta.myproject.vo;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Pagination { //게시판 페이징 처리 로직을 정의한 객체

	private int nowPage=1; //현재 페이지
	
	private int postCountPerPage=10; //페이지당 게시물수
	
	private int pageCountPerPageGroup = 3; //한 그룹당 페이지 수
	
	private int totalPostCount; //총게시물수(데이터베이스에 저장되어 있는)
	
	public Pagination(int totalPostCount) {
		this.totalPostCount = totalPostCount;
	}
	public Pagination(int totalPostCount, int nowPage) {
		this.totalPostCount = totalPostCount;
		this.nowPage = nowPage;
	}
	public int getNowPage() {
		return nowPage;
	}
	/**
	 *  현재 페이지 번호에(nowPage) 해당하는 게시물 리스트의 시작 row number를 반환<br>
	 *  이전 페이지 마지막 번호 +1 <br>
	 *  2 패이지의 시작번호는 1페이지의 마지막번호 5+1 이 시작번호가 된다. 5라는 값은 postCountPerPage<br>
	 *  참고 : 사용자가 페이지번호를 클릭하면 ListController에서 페이지번호를 전달받고<br>
	 *             BoardDAO로부터 총게시물수를 반환받은 후 Pagination 객체를 생성
	 *             findPostList(Pagination) 에 잔달하여 현 페이지에 맞는 게시물 리스트를 반환받을 때 사용하기 위한 메서드
	 *  
	 */
	public int getStartRowNumber() {
		return (this.nowPage-1)*this.postCountPerPage+1;
	}
	/**
	 * 현재 페이지 번호 (nowPage) 에 해당하는 게시물 리스트의 게시물 row의 마지막 번호를 반환<br>
	 * nowPage * postCountPerPage 의 연산결과가 게시물의 마지막 번호이나<br>
	 * 만약 totalPostCount(총게시물수) 보다 클 경우에는 totalPostCount(총게시물수가
	 * 현 페이지의 마지막 게시물 row number가 된다.
	 * @return
	 */
	public int getEndRowNumber() {
		int endRowNumber= this.nowPage*this.postCountPerPage;
		if(endRowNumber> this.totalPostCount) 
			endRowNumber =  this.totalPostCount;
		
		return endRowNumber;
	}
	/**
	 *  총 페이지 수를 반환<br>
	 *  
	 *  totalPostCount / postCountPerPage 연산값의 나머지가 0 이면 나눈값이 총 페이지수<br>
	 *  totalPostCount / postCountPerPage 연산값의 나머지가 0 아니면 나눈값+1이 총 페이지수<br>
	 *  
	 *  
	 *  예) 게시물수 1 2 3 4 5    6 7 8 9 10    11
	 *                   1page       2page       3page
	 *  
	 * @return
	 */
	public int getTotalPage() {
		int totalPage=totalPostCount/postCountPerPage;
		if(totalPostCount%postCountPerPage!=0) {
			totalPage+=1;
		}
		return totalPage;
	}
	
	/**
	 * 
	 * 총페이지 그룹수를 반환 <br>
	 * getTotalPage() 총페이지수 / pageCountPerPageGroup -> 나머지가 0이면 나눈값이 총그룹수<br>
	 * -> 나머지가 존재하면 나는값 +1 한 값이 총 그룹수
	 * 
	 * 예) 총게시물수 48게
	 * 		페이지           1 2 3 4         5 6 7 8       9 10
	 *      페이지그룹        1그룹              2그룹      3그룹
	 * @return totalPageGroup
	 */
	public int getTotalPageGroup() {
		int totalPageGroup =getTotalPage()/pageCountPerPageGroup;
		if(getTotalPage()%pageCountPerPageGroup!=0) {
			totalPageGroup+=1;
		}
		return totalPageGroup;
	}
	/**
	 * 현재 페이지가 속한 페이지 그룹이 몇번째 그룹인지를 리턴
	 * nowPage/ pageCountPerPageGroup 값의 나머지가 0이면  현재 페이지 그룹
	 * nowPage/ pageCountPerPageGroup 값의 나머지가 0이 아니면 나눈값+1이  현재 페이지 그룹
	 * 
	 * 예) 현제 페이지가 7 page <br>
	 *       page  1 2 3 4     5 6 7
	 *              1그룹       2그룹
	 *              
	 * @return newPageGroup
	 */
	public int getNowPageGroup() {
		int nowPageGroup=nowPage/pageCountPerPageGroup;
		if(nowPage%pageCountPerPageGroup!=0) {
			nowPageGroup +=1;
		}
		return nowPageGroup;
	}
	/**
	 *  현재 페이지가 속한 그룹의 시작 페이지 번호를 반환<br>
	 *  
	 *  이전 페이지 그룹 *pagetCountPerPageGroup + 1 => 현재 페이지 그룹의 시작번호
	 *  
	 *  현재 페이지 그룹 getNowPageGroup -> 2 이면<br>
	 *   이전 페이지그룹 (2-1)*pageCountPerPageGroup + 1 => 현재 페이지 그룹의 시작번호
	 *  
	 *  
	 *  pageNO         1 2 3 4       5 6 7 8     9 10
	 *                     1그룹          2그룹      3그룹 
	 * @return startPage
	 */
	
	public int getStartPageOfPageGroup() {	
		return (getNowPageGroup()-1)*pageCountPerPageGroup+1;
	}
	
	/**
	 *  pageNO         1 2 3 4       5 6 7 8     9 10 <br>
	 *                     1그룹          2그룹      3그룹 <br>
	 *                     
	 * getNowPageGroup()*pageCountPerPageGroup => 마지막 페이지 번호
	 * 단 위의 연산값이 getTotlaPage() 즉 총페이지수보다 크면
	 * getTotalPage() 값이 마지막 페이지 번호가 된다.
	 * @return endPage
	 */
	public int getEndPageOfPageGroup() {
		int endPage = getNowPageGroup()*pageCountPerPageGroup;
		if(endPage>getTotalPage()) {
			endPage= getTotalPage();
		}
		return endPage;
	}
/**
 *  이전 페이지 그룹이 존재하는 지 여부를 리턴<br>
 *  getNowPageGroup() 이 1보다 크면 이전 페이지 그룹이 존재<br>
 * @return flag
 */
	public boolean isPreviousPageGroup() {
		boolean flag = false;
		if(getNowPageGroup()>1)
			flag = true;
			return flag;
	}	
	/**
	 * 다음 페이지 그룹이 존재하는 지 여부를 리턴
	 * getTotalPageGroup() 보다 getNowPageGroup() 이 작으면 
	 * @return flag
	 */
	public boolean isNextPageGroup() {
		boolean flag = false;
		if(getTotalPageGroup()>getNowPageGroup())
			flag = true;
		return flag;
	}
}




















