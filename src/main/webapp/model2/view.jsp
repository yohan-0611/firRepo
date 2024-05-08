
<%@page import="Prj4.dtos.mvcboard.MVCBoardDTO"%>
<%@page import="Prj4.daos.mvcboard.MVCboardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
     int idx = Integer.parseInt(request.getParameter("idx"));
   	 MVCboardDAO dao = MVCboardDAO.getInstance();
     MVCBoardDTO dto = dao.getArticle(idx);
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!--수정하기 페이지로 이동 idx 값 도 같이 이동  -->
	<form action="/prj4/UpControllers" name="upForm">
		<input type ="hidden" name = "idx" value = "<%=dto.getIdx() %>">
		<table border="1" width="90%">
			<tr>
				<td>
					글제목
				</td>
				<td>
					<input type="text" name="title" style="width: 90%" value = "<%= dto.getTitle()%>">
				</td>
			</tr>
			<tr>
				<td>
					글내용
				</td>
				<td>
					<input name="content" style="width: 90%; height: 100px" value = "<%= dto.getContent()%>"></input>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
               <button type="submit">수정하기</button>
               <!--글 삭제 페이지로 이동  -->
               <button type="button" onclick = "location.href='/prj4/DelControllers?idx=<%=dto.getIdx()%>'">삭제</button>
               <!--리스트 페이지로 이동  -->
               <button type="button" onclick="location.href='/prj4/ListControllers'">목록보기</button>
            </td>	
			</tr>

		</table>
	</form>
</body>
</html>