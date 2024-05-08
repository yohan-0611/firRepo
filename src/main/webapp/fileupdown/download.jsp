<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--
jsp 를 이용한 파일 다운로드
제일 중요한건, 브라우조의 링크를 이용한 다운이 아니기 때문에, 스트림을 통해서 페이지 응답시에 파일을 다운시켜야 합니다.
이말은 jsp 에서 응답시에 outputstream 을 생성후, 바이너리를 write 해야 한다는 뜻임.
이때 브라우저에 응답되는 데이터가 바이너리라고 헤더를 보내야 하는데, 이 부분은 고정적으로 사용되기 때문에 알아만 두세요.
그리고, 파일의 크기도 주면 좋음.
위 순서대로 파일을 다운시켜 볼테니, 방법만 잘 기억하세요.
  -->
<%
String path = application.getInitParameter("folderName");
//순서 : 다운대상에 스트림을 연결하고, read 한다음, 클라이언트 부라우저에 바이너리데이터가 쓰여질거라고(즉 다운될거라고)
//설정을 해줘야 함(이건 고정값임, 참고로 크기도 줘야함), 다음 응답 객체인 response 객체로 부터 outStream 을 얻은후
//read 한 데이터를 write 하면됨.
  
String fileName = request.getParameter("fname");
String savedName = request.getParameter("nf");
out.print(fileName);
out.print(savedName);
//스트림 연결. 자바로 해도 되는데, 그럴려면 response 객체를 다시 넘겨야 합니다.
try {
	File file = new File(path, savedName);
	
	//파일인풋을 연결해서, 다운로드 할 파일을 READ 합니다.
	FileInputStream fis = new FileInputStream(file);
	
	//이번엔 위에서 연결된 파일을 읽어서 클라이언트에게 보낼건데, 그전에 mime type(application/octet-stream) header 설정부터 해야합니다.
	response.reset();
	response.setContentType("application/download; utf-8");
	response.setHeader("Content-type", "application/octet-stream");
	response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
	response.setHeader("Content-length",file.length() + ""); // 헤더는 모두 문저열 형식이어야함
	
	//출력 스트림의 버퍼 비우기
	out.clearBuffer();
	//response 객체를 이용한 inputStream 얻어내기.
	OutputStream os = response.getOutputStream();
	
	//여기까지 로직에 대한 설명 : 위에서 파라미터로 오는 저장된 파일이름(rename 된 파일명) 과, orgin 이름을 파람으로 받아내고
	//FileInputStream 을 통해서 다운시킬 파일에 대한 스트림 생성, response 객체를 이용한 클라이언트에 대한 outputstream 객체
	//생성(얻어내기), 중간에 응답 데이터의 마임타입 및 헤더 설정.
	//이후부터는 read --> write 하기만 하면됨.
	
	//스트림계열은 read() 할때마다 1byte 를 읽어서 int 로 리턴함. write(int byte) 파람타입은 int 이나
	//무조건 하나의 byte 만 씁니다. 이말은 여러분이 예를들어 int a = 1 을 선언하고 write(a); 하게되면
	//무조건 a의 첫 바이트(플랫폼기반의 4byte 로 encoding 된) 만 쓰게 됩니다. 따라서 읽어보면 꺠진형태가 보여지죠
	//이렇게 read(), write() 의 기본 메서드를 사용하면, 효율성이 떨어지므로, byte[] 을 이용한 read/write 를 하는
	//메서드도 제공합니다. 이때 read(byte[] b) 의 return 타입도 int 인데, 그 의미는 새롭게 read() 한 byte 의
	//갯수를 리턴합니다. 그리고 마지막으로 읽은 갯수가 하나도 없을때는 -1 을 리턴하고, 이떄 byte[] 내의 값은
	//마지막으로 read() 하기전의 값으로 유지됩니다.
	
	//입출력에 사용될 byte 배열을 생성합니다.
	byte[] buffer = new byte[(int)file.length()];
	int readBuffer = 0;
	while((readBuffer = fis.read(buffer)) > 0){
		os.write(buffer,0,readBuffer);
	}
	
	fis.close();
	os.close();
	
	
	
	
	

	
	

	
	
} catch (Exception e) {
	e.printStackTrace();
}


%>