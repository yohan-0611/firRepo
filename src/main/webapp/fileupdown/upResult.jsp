<%@page import="Prj4.daos.file.FileDAO"%>
<%@page import="Prj4.utils.MyFileUtils"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
ArrayList<String> uplist = (ArrayList<String>) request.getAttribute("uploadFiles");
FileDAO dao = FileDAO.getInstance();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>파일 업로드 결과 총[${upcnt }]개의 파일 업로드 완료됨</h2>
	업로드 파일 목록
	<ul>
		<%
		for (String fname : uplist) {
			String savedName = dao.getSaveFName(fname); 
		%>
		<li><a href='/prj4/fileupdown/download.jsp?fname=<%=fname%>&nf=<%=savedName%>'><%=fname %><%=savedName%></a></li>
		<%
		}
		%>
	</ul>
</body>
</html>