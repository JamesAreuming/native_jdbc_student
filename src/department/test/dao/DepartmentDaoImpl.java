package department.test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import department.test.dto.Department;

//IMPLEMENT : 도구
public class DepartmentDaoImpl implements DepartmentDao {
	//DAO를 singleton pattern , 하나만 생성되도록 한다
	
	private static final DepartmentDaoImpl instance = new DepartmentDaoImpl(); //static final
	
	private DepartmentDaoImpl() {} //public을 private으로
	
	
	public static DepartmentDaoImpl getInstance() {
		return instance;
	}

	@Override //SELECT
	public List<Department> selectDepartmentByAll(Connection con) throws SQLException {
		String sql = "select deptno, deptname, floor from department";
		List<Department> list = new ArrayList<Department>();
		try(PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()){
			while(rs.next()) {
				list.add(getDepartment(rs));
			}	
		}
		return list;
	}
	
	@Override //INSERT
	public int insertDepartment(Connection con, Department department) throws SQLException {
		String sql ="insert into department values(?,?,?)";
		int res = -1;
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, department.getDeptNo());
			pstmt.setString(2, department.getDeptName());
			pstmt.setInt(3, department.getFloor());
			System.out.println(pstmt);
			res = pstmt.executeUpdate();
		}
		return res;
	}


	@Override //UPDATE
	public int updateDepartment(Connection con, Department department) throws SQLException {
		String sql ="update department set deptname= ?,floor=? where deptno = ?";
		int res = -1;
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, department.getDeptName());
			pstmt.setInt(2, department.getFloor());
			pstmt.setInt(3, department.getDeptNo());
			System.out.println(pstmt);
			res = pstmt.executeUpdate();
		}
		return res;
	}


	@Override //DELETE
	public int deleteDepartment(Connection con, Department department) throws SQLException {
		String sql ="delete from department where deptno = ?";
		int res = -1;
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, department.getDeptNo());
			System.out.println(pstmt);
			res = pstmt.executeUpdate();
		}
		return res;
	}
	
	//GETDEPARTMENT
	private Department getDepartment(ResultSet rs) throws SQLException {
		int deptNo = rs.getInt("deptno");
		String deptName = rs.getString("deptname");
		int floor = rs.getInt("floor");
		return new Department(deptNo, deptName, floor);
	}

}
