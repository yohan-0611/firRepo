	package Prj4.controllers;

import jakarta.servlet.ServletContext;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.BoardPagingIdx;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Prj4.daos.mvcboard.MVCboardDAO;
import Prj4.dtos.mvcboard.MVCBoardDTO;

/**
 * Servlet implementation class ListControllers
 */
public class ListControllers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * list.do 요청이 오면 이 컨트롤러가 응답을 받아냅니다.
		 *  
		 *  +                                                  0
		 *  
		 * 1.DAO 연결해서 list 항목을 얻어내고, 얻어낸 항목을 viewer 로 전달하기 위해 setAttribute(,...,...)
		 * 합니다.
		 * 
		 * 2. list 를 보여줄 viewer 를 지정하고, 해당 view 를 forward() 합니다.
		 * 
		 * 3. 검색 키워드가 있는지도 확인합니다.
		 * 
		 * 4. paging 처리도 하기위해서 web.xml 로 부터 목록수 perPage 의 값과, 페이지수 preBlock 의 값을 get
		 * 합니다.
		 * 
		 * 
		 */

		// DAO 생성
		MVCboardDAO dao = MVCboardDAO.getInstance();

		// viewer 에게 forward 시 전달될 map 객체 생성해서 목적에 따른 key = value 로 set 후, foward 예정임.
		Map<String, Object> mvcMap = new HashMap<String, Object>();

		// 페이징 처리에 필요한 설정값 가져오기.
		// web.xml 에 접근할수 있는 객체는 servletContext, 일명 Context 객체임 이것부터 get 해야함.
		ServletContext context = getServletContext();
		int pageSize = Integer.parseInt(context.getInitParameter("POSTS_PER_PAGE"));
		int blockPage = Integer.parseInt(context.getInitParameter("PAGES_PER_BLOCK"));

		// 뷰에 전달할 키워드 검색 항목 get
		String searchField = request.getParameter("searchField");
		String searchWord = request.getParameter("searchWord");
		if (searchWord != null) {

			if (!searchWord.isEmpty()) {
				mvcMap.put("searchField", searchField);
				mvcMap.put("searchWord", searchWord);
			}
		}

		// 전체 게시물수 get
		int totalPostCnt = dao.selectCount(searchField, searchWord);
		mvcMap.put("totalPostCnt", totalPostCnt);

		// 페이징 처리 연산
		int pageNum = 1;
		String tempPage = request.getParameter("pageNum");

		if (tempPage != null && !tempPage.equals("")) {
			pageNum = Integer.parseInt(tempPage);
		}

		// List 목록에 출력할 페이징 연산
		int start = (pageNum - 1) * pageSize + 1;
		int end = pageNum * pageSize;
		mvcMap.put("start", start);
		mvcMap.put("end", end);

		// 여기까지의 산출된 데이터를 모두 JSP 에 보내야 합니다.
		// 이번엔 실제 글목록을 리턴 받아서 viewer 에게 보냅니다.
		List<MVCBoardDTO> lists = dao.selectList(searchField, searchWord, start, end);
		
		String pagingControl = BoardPagingIdx.getPaging(totalPostCnt, pageSize, blockPage, pageNum, tempPage);
		mvcMap.put("pagingControl", pagingControl);
		mvcMap.put("pageSize", pageSize);
		mvcMap.put("pageNum", pageNum);

		request.setAttribute("list", lists);
		request.setAttribute("mvcMap", mvcMap);

		request.getRequestDispatcher("model2/list.jsp").forward(request, response);

	}
//http://localhost/prj4/ListControllers 안되면 이거 넣고 실행
}
