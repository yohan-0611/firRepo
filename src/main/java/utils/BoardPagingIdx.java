package utils;

public class BoardPagingIdx {
	// 게시판의 페이징 처리를 위한 static 메서드 정의 합니다.
	// 앞서서 페이징에 필요한 연산식을 봤는데, 이걸 기반으로 처리하고
	// 결과값을 링크를 포함한 문자열로 jsp에 리턴합니다.
	// 어떻게 처리되어지는지 한번 더 설명할게요.

	public static String getPaging(int totalCnt, int pageSize, int blockPage, int pageNum, String url) {

		String pagingstr = "";

		int pageCnt = (int) (Math.ceil(((double) totalCnt / pageSize)));

		int pageTemp = ((pageNum - 1) / blockPage) * blockPage + 1;
		System.out.println("pageTemp ----> " + pageTemp);
		
		
		if(pageTemp != 1) {
			pagingstr = "<a href = '" + url + "?pageNum=1'>[첫페이지]</a> &nbsp" +
					"<a href ='" +  url + "?pageNum=" + (pageTemp - 1) + "'>[이전 가기]</a>";
			
			
		}
		
		//각 블락에 맞는 페이즈 출력합니다.
		//여기서 중요한건, 실제 글 목록수(전체DB 의 count 수 ) 보다 페이지가 많을때 입니다.(홈페이지 생성)
		//때문에 공페이지가 생성이 되면, 마지막 페이지의 카운트를 계ㅒ산된 페이지수 값으로 설정해야합니다.
		int blockCnt = 1;
		while(blockCnt <= blockPage && pageTemp <= pageCnt) {
			//현재 페이지, 즉 사용자가 요청한 페이지의 리스트는 보고있는 중이기때문에 링크를 잡지않고, 나머지 페이지만
			//루프하면서 링크를 잡아줍니다. 이때, 파라미터 값으로는 pageTemp 값을 사용합니다.
			if(pageTemp == pageNum) {
				pagingstr += "&nbsp;" + pageTemp + "&nbsp";// 현재페이지 idx 양옆에 공백	
			}else {
				pagingstr += "&nbsp;<a href ='" + url + "?pageNum=" + pageTemp +  "' >" + pageTemp + "</a>&nbsp";
				
			}
			
			pageTemp++;
			blockCnt++;
		}
		if(pageTemp <= pageCnt) {
			pagingstr += "<a href='" + url + "?pageNum="+ pageTemp + "'>[다음블록]</a> &nbsp";
			pagingstr += "<a href='" + url +  "?pageNum="+ pageCnt + "'>[마지막 페이지]</a>";
		}

		return pagingstr;
	}
}
