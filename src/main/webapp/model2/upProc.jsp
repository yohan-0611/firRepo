
<%@page import="utils.JSFunction"%>
<%@page import="Prj4.daos.mvcboard.MVCboardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    int idx = Integer.parseInt(request.getParameter("idx"));      
    %>
    
<jsp:useBean id="dto" class="Prj4.dtos.mvcboard.MVCBoardDTO">  
  
   <jsp:setProperty property="*" name="dto"/>
   <jsp:setProperty property="idx" name="dto" value="<%=idx %>"/>
</jsp:useBean>

<%
MVCboardDAO dao = MVCboardDAO.getInstance();
int res = dao.upArticle(dto);

//성공시 알람 띄우고 다시 리스트 페이지로 이동
if(res == 1 )
	JSFunction.alertLocation("수정됨", "/prj4/ListControllers", out);
%>