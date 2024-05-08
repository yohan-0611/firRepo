package Prj4.controllers;

import jakarta.servlet.ServletContext;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.JspWriter;
import utils.JSFunction;

import java.io.IOException;

import Prj4.daos.mvcboard.MVCboardDAO;
import Prj4.dtos.mvcboard.MVCBoardDTO;


/**
 * Servlet implementation class ListControllers
 */
public class UpControllers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @param out 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response, JspWriter out)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		MVCboardDAO dao = MVCboardDAO.getInstance();
		MVCBoardDTO dto = new MVCBoardDTO();
    	String idx = request.getParameter("idx");
    	String title = request.getParameter("title");
    	String content = request.getParameter("content");
    
    	dto.setContent(content);
    	dto.setTitle(title);
    	dto.setIdx(Integer.parseInt(idx));
    	dao.upArticle(dto);
    	
		request.getRequestDispatcher("/ListControllers").forward(request, response);
		}

}

