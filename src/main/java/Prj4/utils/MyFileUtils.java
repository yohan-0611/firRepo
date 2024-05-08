package Prj4.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

public class MyFileUtils {
	/*
	 * 전달된 request 를 읽어서, param 을 얻어냅니다. 이후, 파일업로드 API 인 Tomcat 의 part 객체를 통해서 파일정보
	 * 등을 얻어냅니다. 이 API 는 코드내에서 다시 설명함 origin name, save name(변경이름) 을 생성하고, 첨부된 파일에
	 * 대한 정보 정리를 끝냅니다. 이후, 다른 파라미터(일반 파라미터) 정보를 얻어냅니다. (이 정보는 위 정보와 함꼐 DB 에 저장됩니다).
	 * 저장하기 위해서 DAO 의 메서드를 이용, DB 에 정보를 저장합니다. 이후 컨트롤로에 파일 목록을 리턴시켜서, 시용자가 업로드한 파일의
	 * 목록을 볼수있도록 합니다.
	 */
	private static final String storage = new File("D:/uploads").getAbsolutePath();
	
	//파일명을 Timestamp 를 추가해서 변경후 되돌려줌. 최종적으로는 DB 에는 orgin, rename 두개가 insert 됨
	public static String rename(String orgName) {
		String ext = orgName.substring(orgName.indexOf("."));

		//날짜를 특정 포맷으로 생성후 String 으로 리턴받기
		String now = new SimpleDateFormat("yyyyMMdd_Hmss").format(new Date()) + new Random(System.currentTimeMillis()).nextLong();
		String newFileName = now + ext;
		
		//기존 파일명을 새 파일명을 ㅗ변경하기
		//그러기 위해선 기존 파일에 대한 참조가 있어야 함
		//File 객체를 이용함
		
		File old = new File(storage + File.separator + orgName);
		File newFile = new File(storage + File.separator + newFileName);
		
		old.renameTo(newFile);
		System.out.println(newFile);
		return newFileName;
		
		
		
		
	}

	// 파일 업로드 하고 rename 을 처리하는 메서드 정의합니다.
	// 기능만을 사용할 에정이므로 static 으로 메서드를 처리합니다.
	public static ArrayList<String> uploadFile(HttpServletRequest request) throws Exception {
		// 업로드 완료후 리턴될 origin FileName
		String originName = null;

		// 멀티파일 업로드 후 오리진 이름을 담은 AraayList 생성
		ArrayList<String> fileNames = new ArrayList();

		// 하나 이상의 업로드 파일은 Parts 라는 객체가 담당함
		// 배열 형식으로 구성되어 있고, 순회 하면서 Part 객체를 리턴받은후
		// 오리진 파일이름 get 하고, write() 해주면 끝임.

		Collection<Part> parts = request.getParts();

		for (Part part : parts) {
			if (!part.getName().equals("ofile"))
			continue;

			String partHeader = part.getHeader("content-disposition");

			String[] arr = partHeader.split("filename=");

			String orgFileName = arr[1].trim().replace("\"", "");

			if (!orgFileName.isEmpty()) {
				part.write(storage + File.separator + orgFileName);
			}
			fileNames.add(orgFileName);
		}
		// Part 객체 : Tomcat10 에 내장된 파일 업로드 처리 객체.
		// 해당 객체는 request 객체를 통해서 얻어낼수있음.
		// 기본 파라미터로는 폼데이터에서 전송된 파일 input tag 의 name 값을 key 로 주면
		// 해당 파일에 대한 Part 객체를 리턴함.
		// 이후, 클라이언트의 header 중 content - disposition 의 값을 읽어내면, 실제 전송된 파일의 정보를 얻어낼수
		// 있습니다.
		// 이 얻어낸 파일 정보중 필요한 부분만 취해서 다음 로직에 연결함.
		System.out.println(storage);
		// 아래는 파일 업로드 로직임.
		/*
		 * Part part = request.getPart("ofile"); String partHeader =
		 * part.getHeader("content-disposition");
		 * 
		 * String[] arr = partHeader.split("filename=");
		 * 
		 * String orgFileName = arr[1].trim().replace("\"","");
		 * 
		 * if(!orgFileName.isEmpty()) { part.write(storage + File.separator +
		 * orgFileName); }
		 */

		// 여기는 멀티파일 업로드 로직임

		return fileNames;
	}
}
