package department.test.ui.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import department.test.dao.DepartmentDao;
import department.test.dao.DepartmentDaoImpl;
import department.test.dao.EmployeeDao;
import department.test.dao.EmployeeDaoImpl;
import native_jdbc_student.ds.MySqlDataSource;
import department.test.dto.Department;
import department.test.dto.Employee;


public class DepartmentUIService {
	private Connection con;
	private DepartmentDao deptDao;
	private EmployeeDao empDao;
	
	public DepartmentUIService() {
		try {
			con = MySqlDataSource.getConnection();
			deptDao = DepartmentDaoImpl.getInstance();
			empDao = EmployeeDaoImpl.getInstance();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "접속 정보 확인");
		}
	}

	public List<Employee> showEmployeeGroupByDno(Department dept){
		try {
			return empDao.selectEmployeeGroupByDno(con, dept);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Department> showDepartments(){
		try {
			return deptDao.selectDepartmentByAll(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void addDepartment(Department newDept) throws SQLException {
		deptDao.insertDepartment(con, newDept);
	}
	
	public void modifyDepartment(Department dept) throws SQLException{
		deptDao.updateDepartment(con, dept);
	}
	
	public void removeDepartment(Department dept) throws SQLException{
		deptDao.deleteDepartment(con, dept);
	}
}
