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
public class WriteControllers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @param out 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response, JspWriter out)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MVCBoardDTO dto = new MVCBoardDTO();
		dto.setName("요한");
		dto.setContent(request.getParameter("content"));
		dto.setTitle(request.getParameter("title"));
		int result = MVCboardDAO.getInstance().insertArticle(dto);	
		request.getRequestDispatcher("model2/writeForm.jsp").forward(request, response);
		}

}


