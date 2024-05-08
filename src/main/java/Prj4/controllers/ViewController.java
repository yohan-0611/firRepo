package Prj4.controllers;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import Prj4.daos.mvcboard.MVCboardDAO;
import Prj4.dtos.mvcboard.MVCBoardDTO;

/**
 * Servlet implementation class ViewController
 */
public class ViewController extends HttpServlet {
   private static final long serialVersionUID = 1L;

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
      MVCboardDAO dao = MVCboardDAO.getInstance();
      MVCBoardDTO dto = null;
      
      int idx = Integer.parseInt(request.getParameter("idx"));
      System.out.println(idx);
      dao.increVisitCnt(idx);
      
      dto = dao.getArticle(idx);
      
      //dto, pagenum 등 필요 요소를 선택해서 view.jsp로 넘긴다
      request.setAttribute("dto", dto);
      request.getRequestDispatcher("model2/view.jsp").forward(request, response);
   }

}