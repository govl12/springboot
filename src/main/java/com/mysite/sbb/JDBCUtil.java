package com.mysite.sbb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCUtil {
		//DB를 연결 설정하는 클래스 
	
	//1.DB를 연결하는 메소드 
		//static 키워드 : 객체 생성 없이 클래스이름을 바로 호출 할 수 있음.
	public static Connection getConnection() {
		String driver ="oracle.jdbc.driver.OracleDriver";
		//String driver = "org.h2.Driver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		//String url = "jdbc:h2:tcp://localhost/~/test";
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn =  DriverManager.getConnection(url,"C##HR","1234"); // 커넥션이 잘되면..
												//URL, 계정명, 암호 
			//conn = DriverManager.getConnection(url,"sa","");
	
			//확인 후 주석 처리 
			System.out.println("DB에 잘 연결 되었습니다.");

			return conn;
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("DB연결에 실패했습니다.");
		}
		return conn;
		//return null;//커넥션이 안되면.. 객체 리턴
		
		
	}	//메소드 호출하면 Connection 객체를 리턴하게 됨! 
	
	
	
	//2. DB연결을 제거하는 메소드 : Connection, PreparedStatement 객체를 제거 
		//Insert, Update, Delete 
	public static void close(PreparedStatement pstmt, Connection conn) {	
		if (pstmt!=null) {
			try {
				if(!pstmt.isClosed()) {	// pstmt객체가 close되지 않은 상태(제거되지 않은 상태)
					pstmt.close();
					System.out.println("pstmt 객체 close");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				pstmt = null;
			}
		}
		if(conn!=null) {
			try {
				if(!conn.isClosed()) {
					conn.close();
					System.out.println("conn 객체 close()");
					
				}
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				conn = null;
			}
							
			}	
		}		
		
			//3. DB연결을 제거하는 메소드 : Connection, PreparedStatment , ResultSet객체를 제거
		//Select 
		public static void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {	
			if (rs!=null) {
				//try catch 사용하는 이유 : 오류가 발생되더라도 멈추지 말라고..
				try {
					if(!rs.isClosed()) {	// pstmt객체가 close되지 않은 상태(제거되지 않은 상태)
						rs.close();
					}
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
					rs = null;
				}
			}
			
			
			if (pstmt!=null) {
				try {
					if(!pstmt.isClosed()) {	// pstmt객체가 close되지 않은 상태(제거되지 않은 상태)
						pstmt.close();
					}
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
					conn = null;
				}
			}
			
			
			if(conn!=null) {
				try {
					if(!conn.isClosed()) {
						pstmt.close();
					}
				}catch (Exception e) {
					e.printStackTrace();
				}finally {
					conn = null;
				}
								
				}	
			}		
		

	public static void main(String[] args) {
		//public static void main 제대로 안쓰면 큰일난당..ㅇㅇ..
		
		//ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋmainzㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ
		JDBCUtil jdbc = new JDBCUtil();
		
		jdbc.getConnection();
		
	}
	}