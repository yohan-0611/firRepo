package Prj4.controllers;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import Prj4.daos.file.FileDAO;
import Prj4.dtos.file.FileDTO;
import Prj4.dtos.mvcboard.MVCBoardDTO;
import Prj4.utils.MyFileUtils;



/**
 * Servlet implementation class FileController
 */
@MultipartConfig(
maxFileSize = 1024 * 1024 * 10,
maxRequestSize = 1024 * 1024 * 100
)
public class FileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//파일 요청시 전달되는 일반 파라미터와, 파일이름을 받아서 DB 에 저장하도록하는 DAO 를 호출하는 메서드 정의.
	private void insert(int idx,String oldFname, String newFname, HttpServletRequest request ) {
		//일반 파라미터 정보 얻기
		String title = request.getParameter("title");
		String[] cate = request.getParameterValues("cate");
		
		StringBuffer sb = new StringBuffer();
		if(cate == null) {
			sb.append("선택된 카테고리 없음");
		}else {
			for(String s : cate) {
				sb.append(s + ", ");
			}
		}
		
		//모든 DTO 정보 완성 했으니 DTO 에 담기
		MVCBoardDTO dto = new MVCBoardDTO();
		dto.setOfile(oldFname);
		dto.setSfile(newFname);
		dto.setIdx(idx);
		FileDAO dao = FileDAO.getInstance();
		dao.insertFile(dto);
		
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		FileDAO fileDAO = FileDAO.getInstance();
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		try {
			ArrayList<String> orgFileNames =  MyFileUtils.uploadFile(request);
			
			//원래 이름을 새로운 이름으로 변경합니다.
			for(String fileName : orgFileNames) {
				String savedFileName = MyFileUtils.rename(fileName);
				
				insert(idx,fileName, savedFileName, request);
				
				//두개의 파일 이름을 얻었으면, private 메서드인 insert() 를 호출, 파일이름을 파람으로 주고
				//나머지 일반 요청 정보를 get 해서 DTO 에 모두 set 후 DB 에 insert 하도록 함
				
				
			}
			
			//코드가 여기로 오면 insert 모두 완료. 사용자에게 업로드된 목록과, 결과를 viewer 를 통해 넘깁니다.
			request.setAttribute("uploadFiles", orgFileNames);
			request.setAttribute("upcnt", orgFileNames.size());
			request.getRequestDispatcher("/fileupdown/upResult.jsp").forward(request, response);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
