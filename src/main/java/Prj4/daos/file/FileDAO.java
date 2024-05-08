package Prj4.daos.file;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Result;

import Prj4.dtos.file.FileDTO;
import Prj4.dtos.mvcboard.MVCBoardDTO;

public class FileDAO {

	private static FileDAO instance = new FileDAO();

	private FileDAO() {

	}

	public static FileDAO getInstance() {
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

	public String getSaveFName(String originName) {
		String sql = "Select SFILE from mvcboard where ofile = ?";
		String theName = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, originName);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				theName = rs.getString("sfile");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {

			}
		}
		return theName;
	}

	// 업로드 파일 정보 저장 메서드 정의
	public int insertFile(MVCBoardDTO dto) {
		int res = 0;
		String sql = "UPDATE mvcboard SET ofile = ?, sfile = ? WHERE idx = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getOfile());
			pstmt.setString(2, dto.getSfile());
			pstmt.setInt(3, dto.getIdx());

			res = pstmt.executeUpdate();
			
			System.out.println("ofile : " + dto.getOfile() + "sfile : " + dto.getSfile() + "idx : " + dto.getIdx());
			System.out.println("파일정보 잘 저장됨");

		} catch (Exception e) {
			System.out.println("파일 INSERT 예외 발생함");
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {

			}
		}
		return res;
	}

}