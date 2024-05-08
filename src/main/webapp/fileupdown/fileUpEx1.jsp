<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- JSP 를 이용한 파일 업로드
	     톰켓10 이하에서는 파일 업로드를 위한 lib 중 하나(.jar) 를 buildpath(의존성 추가)에 추가해서 API 를 이용해서 업로드를 시켰는데.
	     대표적인 lib로는 cos.jar 이 있음
	     톰켓10 이후부터는 자체적으로도 업로드 모듈을 컨테이너에 추가해서 배포되기 떄문에, 따로 의존성 추가를 하지 않고,
	     jakarta 라이브러리를 이용해서 업로들 진행할수 있음.
	     part 라는 객체인데, 자세한건 나중에
	     중요한건, 파일의 관리인데 이부분 설명함
	     
	     파일업로드시에 일반적으로 파일을 선택하는 input tag 를 사용하는데, 반드시 post 방식으로,
	     더 중요한건, enctype = "multipart/form-data" 라는 속성을 선언해야 파일 전송이 이뤄짐 
	     위처럼 enctype 으로 속성을 선언하면, 폼 전송시 파일과 함께 파라미터도 같이 전송되는데, 이때, 서버에서는
	     ㅇ파일 파라미터와 일반 파라미터를 구분해야 합니다.
	     이때 파일 업로드 API 가 이구분점을 객체와 메서드로 제공하니 -->
	     <%
	     int idx = Integer.parseInt(request.getParameter("idx"));// idx 값을 받아옵니다.
		 %>
	     <h2>파일 업로드 폼 </h2>
	     
	     <form action="../multipleProc.do?idx=<%=idx%>" method="post" enctype="multipart/form-data">
	     제목 : <input type="text" name="title"><br>
	     카테고리 : <input type="checkbox" name="cate" value="사진" checked="checked">사진
	      <input type="checkbox" name="cate" value="코드소스">소스코드
	      <input type="checkbox" name="cate" value="오피스파일">오피스파일<br>
	     
	     <!-- 파일 선택창은 input type 을 file 로 해야합니다  -->
	     첨부파일 : <input type="file" name="ofile" multiple="multiple">
	     <input type="submit" value="전송">
	     </form>
</body>
</html>