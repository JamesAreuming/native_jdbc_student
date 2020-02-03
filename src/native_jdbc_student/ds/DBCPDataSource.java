package native_jdbc_student.ds;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBCPDataSource {
	private static BasicDataSource ds = new BasicDataSource();
	
	static {
		try(InputStream is = ClassLoader.getSystemResourceAsStream("dbcp.properties")){
			Properties prop = new Properties();
			prop.loadFromXML(is);
			
			ds = new BasicDataSource();
			ds.setUrl(prop.getProperty("jdbcUrl"));
			ds.setUsername(prop.getProperty("dbUser"));
			ds.setPassword(prop.getProperty("dbPwd"));
			ds.setMinIdle(Integer.parseInt(prop.getProperty("minIdle")));
			ds.setMaxOpenPreparedStatements(Integer.parseInt(prop.getProperty("MaxOpenPreparedStatements")));
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
	
	private DBCPDataSource() {}
}
