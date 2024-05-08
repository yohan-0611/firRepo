<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
a {
	text-decoration: none;
}
</style>
<title>Insert title here</title>
</head>
<body>
	<h2>MVC2 형식의 게시판 List</h2>
	<form action="ListControllers">
		<table border="1" width="90%">
			<tr>
				<td align="center"><select name="searchField">
						<option value="title">제목</option>
						<option value="content">내용</option>
				</select> <input type="text" name="searchWord"> <input type="submit"
					value="검색"></td>
			</tr>
		</table>
	</form>
	<table border="1" width="90%">
		<tr>
			<td width="10%">번호</td>
			<td width="*">제목</td>
			<td width="15%">작성자</td>
			<td width="10%">조회수</td>
			<td width="15%">작성일</td>
			<td width="8%">첨부</td>
		</tr>

		<!--jstl 을 이용한 목록 loop 제일 먼저 글목록이 존재하는 여부부터 확인합니다.  -->

		<c:choose>
			<c:when test="${empty list }">
				<!-- 글이 존재하지 않을때  -->
				<tr>
					<td colspan="6" align="center">등록된 게시물 없음</td>
				</tr>
			</c:when>
			<c:otherwise>
				<!-- 글이 하나이상 존재할때 -->
				<c:forEach items="${list }" var="ele" varStatus="loop">
					<tr align="center">
						<td>${mvcMap.totalPostCnt - ((mvcMap.pageNum - 1) * mvcMap.pageSize + loop.index)}
						</td>
						
						<td align="center" ><a href="ViewController?idx=${ele.idx}">${ele.title}</a>
						</td>
						
						<td align="center">${ele.name }</td>
						<td align="center">${ele.visitcount }</td>
						<td align="center">${ele.postdate }</td>
						<!--파일 업로드 하는 페이지로 이동 idx 값도 같이 보내준다.  -->
						<td align="center"><a href = "/prj4/fileupdown/fileUpEx1.jsp?idx=${ele.idx}">파일 업로드</a></td>
							

					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</table>
	<table width="90%">
		<tr align="center"> 
		<!--버튼 클릭시 글쓰기 페이지로 이동  -->
		<form action="/prj4/WriteControllers">
		<button type="submit">글쓰기</button>
		</form>
			<td>${mvcMap.pagingControl}</td>
		</tr>
	</table>
</body>
</html>