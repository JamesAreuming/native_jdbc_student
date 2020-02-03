package native_jdbc_student.ds;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Hikari_DataSource2 {
	private static HikariConfig config;
	private static HikariDataSource ds;

	static {
		config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://localhost/mysql_study?useSSL=false");
		config.setUsername("user_mysql_study");
		config.setPassword("rootroot");
		config.addDataSourceProperty("cachPrepStmts", "true");
		config.addDataSourceProperty("preStmtCacheSize", "250");
		config.addDataSourceProperty("preStmtCacheSqlLimit", "2048");
		ds = new HikariDataSource(config);
	}

	private Hikari_DataSource2() {} // 외부에서 new DataSource() 금지

	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
}
