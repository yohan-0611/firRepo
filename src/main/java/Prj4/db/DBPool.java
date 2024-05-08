package Prj4.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/*
 * 이 클래스는 java의 JNDI API 를 이용해서
 * 서버에 설정한 이름 값으로 리소스(DB Pooler) 를 찾아서 
 * WAS에서 관리하는 Connection 을 리턴하도록 하는 클래스입니다.
 * 로직내용은 변화없이 다 똑같고, 값만 틀림. (DB타입에 따라)
 * 
 * 그러니 외우려고 하지 말고, 그렇구나 이런걸 잉요하는 구나 정도만 이애하삼.
 */
public class DBPool {

   private Connection conn;
   private Statement stmt;
   private PreparedStatement pstmt;
   private ResultSet rs;
   
   private static final DBPool pooler = new DBPool();
   
   private DBPool() {
      init();
   }
   //싱글톤 instance 리턴 메서드
   public static DBPool getPooler() {
      //생성자에서 connection 을 생성하는 init() 호출함. 이놈이 WAS로부터 설정된 Pooler 에서 Connection 생성함
	  
      return pooler;
   }
   

   private void init() {
      //이 코드는 JNDI 를 이용하는 코드
      //고정적이기 떄문에(값만 틀림) 그냥 가져다 사용함. 외우는거 아님
      try {
         //java.naming package 의 Context임
         Context initCtx = new InitialContext();
         DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/myoracle");
         
         this.conn = ds.getConnection();
         
         System.out.println("DB 컨넥션 풀링 성공됨. 커넥션 정보 : " + conn);
         
      } catch (Exception e) {
         System.out.println("컨넥션 풀 예외 발생 : " + e.getMessage());
         e.printStackTrace();
      }
      
   }
   
   public Connection getConnection() {
      return this.conn;
   }
   
   
   
   
   //사용된 DB자원 해제 메서드
   public void close() {
      try {
         if(rs!=null)rs.close();
         if(pstmt!=null)pstmt.close();
         if(stmt!=null)stmt.close();
         if(conn!=null)conn.close();
         
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}