
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@page import="utils.JSFunction"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	//폼 validate 함수 작성
	function validateForm(form) {
		if (form.title.value == "") {
			alert('제목을 입력하세요');
			form.title.focus();
			return false;
		}
		if (form.content.value == "") {
			alert('게시글을 입력세요');
			form.content.focus();
			return false;
		}
	}
	
</script>
</head>
<body>
	<h2>회원제 게시판 글쓰기 폼</h2>
	<form action="/prj4/WriteControllers" name="writeForm">
		<table border="1" width="90%">
			<tr>
				<td>
					글제목
				</td>
				<td>
					<input type="text" name="title" style="width: 90%">
				</td>
			</tr>
			<tr>
				<td>
					글내용
				</td>
				<td>
					<textarea name="content" style="width: 90%; height: 100px"></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit">작성완료</button>
					<button type="reset">재작성</button>
					<button type="button" onclick="location.href='/prj4/ListControllers'">목록(list)보기</button>
				</td>	
			</tr>

		</table>
	</form>

</body>
</html>