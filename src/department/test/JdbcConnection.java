package department.test;

//import는 무조건 java.sql
import java.sql.Connection; //java.sql : 맨아래 import
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import department.test.dto.Department;
import native_jdbc_student.ds.DBCPDataSource;
import native_jdbc_student.ds.Hikari_DataSource;
import native_jdbc_student.ds.Hikari_DataSource2;

public class JdbcConnection {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/mysql_study?useSSL=false";
		String user = "user_mysql_study";
		String password = "rootroot";
		
		selectDepartment(url, user, password);
		
		insertDepartment(url, user, password);
		
		updateDepartment(url, user, password);
		
		deleteDepartment(url, user, password);
	}


	private static void deleteDepartment(String url, String user, String password) {
		String deleteSql = "delete from department where deptno = ?";
		try(Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = con.prepareStatement(deleteSql)){
			pstmt.setInt(1, 9);
			System.out.println("연결성공"+con+pstmt);
			
			int res = pstmt.executeUpdate();
			System.out.println(res);
		} catch (SQLException e) {
			System.err.println("해당 데이터베이스가 존재하지 않거나 계정 및 비밀번호 확인 요청" + e.getErrorCode());
			e.printStackTrace();
		}
	}
	
	
	private static void updateDepartment(String url, String user, String password) {
		String updateSql = "update department set deptname =?,floor=? where deptno=?";
		try(Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = con.prepareStatement(updateSql)){
			pstmt.setString(1, "마케팅");
			pstmt.setInt(2, 11);
			pstmt.setInt(3, 5);
			
			System.out.println(pstmt);
			int res = pstmt.executeUpdate();
			System.out.println(res);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void insertDepartment(String url, String user, String password) {
		String insertSql = "insert into department value(?,?,?)";
		try(Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = con.prepareStatement(insertSql)){
			pstmt.setInt(1, 9);
			pstmt.setString(2, "회계");
			pstmt.setInt(3, 100);
			System.out.println("연결성공"+con+pstmt); // 귀찮더라도 반드시찍어봐야함 insert into department value(9,'회계',100)
			
			int res = pstmt.executeUpdate(); // 리턴타입 int
			System.out.println(res); // 1이 리턴됨 (1개가 들어갔으니)
			
		} catch (SQLException e) {
			System.err.println("해당 데이터베이스가 존재하지 않거나 계정 및 비밀번호 확인 요청" + e.getErrorCode());
			if(e.getErrorCode()==1062) { //에러코드 1062 : 프라이머리키 중복
				JOptionPane.showMessageDialog(null, "해당부서가 이미 존재한다");
			}
			e.printStackTrace();
		}
	}





	private static void selectDepartment(String url, String user, String password) {
		String selectSql = "select deptno, deptname, floor from department";
		try (Connection con = Hikari_DataSource2.getConnection();){
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(selectSql);
			// 1. JDBC 드라이버 로드
//			Class.forName("com.mysql.jdbc.Driver");
			// 2. 데이터베이스 접속
//			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql_study?useSSL=false",
//					"user_mysql_study", "rootroot"); // 두번째껄로 예외처리 //useSSL=false : 빨간문구 없애기
			System.out.println("접속성공" + con);
			
//			Statement stmt = con.createStatement();
//			ResultSet rs = stmt.executeQuery("select deptno, deptname, floor from department");
			List<Department> deptList = new ArrayList<Department>();
			
			while(rs.next()) {
				deptList.add(getDepartment(rs));
//				int deptNo = rs.getInt("deptno");
//				String deptName = rs.getString("deptname");
//				int floor = rs.getInt("floor");
				
//				System.out.printf("%d %s %d %n",deptNo, deptName, floor);
			}
			
			for(Department d:deptList) { //출력
				System.out.println(d);
			}
			
//		} catch (ClassNotFoundException e) {
//			System.err.println("해당 드라이버를 로드할 수 없습니다.");
//			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("해당 데이터베이스가 존재하지 않거나 계정 및 비밀번호 확인 요청" + e.getErrorCode()); // 1045 : DB, 계정명, 비번 틀린 경우
			e.printStackTrace();
		}
	}

	private static Department getDepartment(ResultSet rs) throws SQLException {
		int deptNo = rs.getInt("deptno");
		String deptName = rs.getString("deptname");
		int floor = rs.getInt("floor");
		return new Department(deptNo, deptName, floor);
	}

}
