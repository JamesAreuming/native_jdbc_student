package department.test.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import department.test.dto.Department;

public interface DepartmentDao {
	List<Department> selectDepartmentByAll(Connection con) throws SQLException;
	
	int insertDepartment(Connection con, Department department) throws SQLException;
	int updateDepartment(Connection con, Department department) throws SQLException;
	int deleteDepartment(Connection con, Department department) throws SQLException;
}
