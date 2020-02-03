package native_jdbc_student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JDialog;

import department.test.dao.DepartmentDao;
import department.test.dao.DepartmentDaoImpl;
import department.test.dao.EmployeeDao;
import department.test.dao.EmployeeDaoImpl;
import department.test.dto.Department;
import department.test.dto.Employee;
import department.test.ui.DlgEmployee;
import native_jdbc_student.ds.C3p0DataSource;
import native_jdbc_student.ds.DBCPDataSource;
import native_jdbc_student.ds.Hikari_DataSource;
import native_jdbc_student.ds.Hikari_DataSource2;

public class Main {
	public static void main(String[] args) throws SQLException{
		
		try (Connection con = Hikari_DataSource2.getConnection();) {
			//소속부서사원검색test(con);
			DepartmentDao dao = DepartmentDaoImpl.getInstance();
			//추가할 부서 정보
			Department newDept = new Department(15,"마케팅",11);
			dao.insertDepartment(con,newDept);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
		}
		

		//같은건데 따로씀
//		EmployeeDao dao1 = new EmployeeDaoImpl();
//		EmployeeDao dao2 = new EmployeeDaoImpl();
//		
//		System.out.println(dao1);
//		System.out.println(dao2);
		
		//같은걸로 씀  - 메모리 효율적, 싱글패턴 쓰지 않으면 서버 동시접속자 많으면 서버가 뻗는다.
		//department.test.dao.EmployeeDaoImpl@7852e922
		//department.test.dao.EmployeeDaoImpl@7852e922
		
		
//		for(int i=0;i<100;i++) {
//			EmployeeDao dao3 = EmployeeDaoImpl.getInstance();
//			System.out.println(dao3);
//			EmployeeDao dao1 = new EmployeeDaoImpl();
//			System.out.println(dao1);
//		}

//		EmployeeDao dao4 = EmployeeDaoImpl.getInstance();
//		System.out.println(dao4);
		
//		EmployeeDao dao3 = EmployeeDaoImpl.getInstance();
//		System.out.println(dao3);
//		for(Employee e:dao3.selectEmployeeByAll(Hikari_DataSource2.getConnection())) {
//			System.out.println(e);
//		}
//		
//		
//		DepartmentDao dao1 = DepartmentDaoImpl.getInstance();
//		System.out.println(dao1);
//		for(Department d:dao1.selectDepartmentByAll(Hikari_DataSource.getConnection())) {
//			System.out.println(d);
//		}
		
		
		
//		try (Connection con = Hikari_DataSource.getConnection();) {
//			System.out.println(con);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		try (Connection con = Hikari_DataSource2.getConnection();) {
//			System.out.println(con);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		try (Connection con = DBCPDataSource.getConnection();) {
//			System.out.println(con);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		try (Connection con = C3p0DataSource.getConnection();) {
//			System.out.println(con);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

	}

	private static void 소속부서사원검색test(Connection con) throws SQLException {
		EmployeeDao dao = EmployeeDaoImpl.getInstance();
		Department dept = new Department();
		dept.setDeptNo(1);
		List<Employee> list = dao.selectEmployeeGroupByDno(con, dept);

		for (Employee e : list) {
			System.out.println(e);
		}
		
		DlgEmployee dialog = new DlgEmployee();
		dialog.setEmpList(list);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
}
