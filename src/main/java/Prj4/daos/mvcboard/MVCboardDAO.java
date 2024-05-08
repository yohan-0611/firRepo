package Prj4.daos.mvcboard;

import java.sql.Connection;



import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Prj4.dtos.mvcboard.MVCBoardDTO;
/*
 * pattern2의 DAO 정의
 * */
public class MVCboardDAO {

	private static MVCboardDAO instance = new MVCboardDAO();

	private MVCboardDAO() {

	}

	public static MVCboardDAO getInstance() {
		return instance;
	}

	private Connection getConnection() throws Exception {

		Class.forName("oracle.jdbc.OracleDriver");

		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe", "YOHAN", "1234");

		if (conn != null) {
			System.out.println("컨넥션 잘 생성됨");
		}
		return conn;
	}
	//게시글수 구하는 함수
	public int selectCount(String sField, String sWord) {
		int count = 0;// 게시글 갯수변수

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		String sql = "select count(*) As count from mvcboard";
		
		if(sWord != null) {
			sql += " where " + sField+" " +
		" LIKE '%" + sWord + "%'";
		}
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count");

			}
			close();
		} catch (Exception e) {
			System.out.println("글갯수 가져오기 예외 발생함");
			e.printStackTrace();
		}
		return count;

	}
	
	//검색 함수
	public List<MVCBoardDTO> selectList(String sField, String sWord,int start, int end) {
		/*
		 * String sql2 = "select t2.* from" +
		 * "(select t.*, rownum rnum from ((select * from board where content like '%3%' order by num desc) t"
		 * +"))t2" + "where rnum between 1 and 10";
		 */
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		String sql = "select t2.* from (select t.*, rownum rnum from ((select * from mvcboard";
		if(sWord != null && !sWord.isEmpty()) {
			sql += " where " + sField+" " +
					" LIKE '%" + sWord + "%'";
					}
		sql += " order by idx desc) t ))t2 where rnum between ? and ?";
		List<MVCBoardDTO> lists = new ArrayList<MVCBoardDTO>();
		MVCBoardDTO dto;
		try {
			 pstmt = getConnection().prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			 rs = pstmt.executeQuery();
			

			while (rs.next()) {
				// 이 구문을 탄다는 말은 게시글이 하나 이상 존재한다는 말
				dto = new MVCBoardDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setName(rs.getString("name"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setVisitcount(rs.getInt("visitcount"));
				dto.setOfile(rs.getString("ofile"));
				dto.setSfile(rs.getString("sfile"));
				dto.setDowncnt(rs.getInt("downcnt"));
				dto.setPass(rs.getString("pass"));

				// 위에서 하나의 row 를 DTO 다 set 헀으면, List 에 add 한다
				lists.add(dto);
			}

			if (lists.size() == 0) {
				return Collections.EMPTY_LIST;
			}
			return lists;
		} catch (Exception e) {
			e.getMessage();
		}
		try {
			close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lists;
		
	}
	
	//방문자수 증가 함수
	public void increVisitCnt(int num) {
		String sql = "Update mvcBoard set visitcount = (visitcount + 1) where idx = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();		
	}catch(Exception e){
		e.printStackTrace();
	}
		try {
			close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	//리스트 보여주는 함수
	public MVCBoardDTO getArticle(int num) {
		String sql = "select * from mvcboard where idx = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MVCBoardDTO dto = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new MVCBoardDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setName(rs.getString("name"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setVisitcount(rs.getInt("visitcount"));
				dto.setOfile(rs.getString("ofile"));
				dto.setSfile(rs.getString("sfile"));
				dto.setDowncnt(rs.getInt("downcnt"));
				dto.setPass(rs.getString("pass"));
							
			}
			close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	//글 삭제 함수
	public int deleteArticle(int idx) {
		String sql = "delete from mvcboard where idx = ?";
		Connection conn = null;
		int res = 0;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			res = pstmt.executeUpdate();	
			System.out.println(res);
			
			
	}catch(Exception e){
		e.printStackTrace();
	}
		return res;
	}
	
	//글 수정 함수
	public int upArticle(MVCBoardDTO dto) {
	      int res = 0;
	      String sql = "update mvcboard set title = ?, content = ? where idx = ?";
	      
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      
	      try {
	         conn = getConnection();
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, dto.getTitle());
	         pstmt.setString(2, dto.getContent());
	         pstmt.setInt(3, dto.getIdx());
	         res = pstmt.executeUpdate();   
	         
	         close();
	         
	      } catch (Exception e) {
	         
	         e.printStackTrace();
	      }finally {
	         try {
	            if(pstmt != null)
	               pstmt.close();
	            if(conn != null)
	               conn.close();
	         }catch (Exception e) {
	            
	         }
	      }
	      return res;
	   }
	
	//글 작성 함수
	public int insertArticle(MVCBoardDTO dto) {// void 로 한 이유는 insert 가 되면 무조건 1리턴됨 아님 무조건 예외뜸
		String sql = "insert into mvcboard(idx,title,content,name,pass) values(MVC_Board_seq.nextval,?,?,?,1234)";
		try {
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getName());

			System.out.println(dto.getName() + "글 insert 완료");
			conn.commit();

			try {
				conn.rollback();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return pstmt.executeUpdate();
		} catch (Exception e1) {
			e1.printStackTrace();
			return 0;
		}

	}
	
	//close 함수
	private void close() throws Exception {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		Statement stmt = null;

		if (rs != null)
			rs.close();
		if (pstmt != null)
			pstmt.close();
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();

	}
}
