package department.test.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import department.test.dto.Department;
import department.test.dto.Employee;

public interface EmployeeDao {
	Employee selectEmployeeByDao(Connection con, DepartmentDaoImpl dept) throws SQLException;
	
	List<Employee> selectEmployeeByAll(Connection con) throws SQLException;

	List<Employee> selectEmployeeGroupByDno(Connection con, Department dept)throws SQLException;
	
}
