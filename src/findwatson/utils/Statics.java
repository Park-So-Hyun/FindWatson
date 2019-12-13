package findwatson.utils;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class Statics {
	public static BasicDataSource bds = new BasicDataSource();
	
	static {
		bds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		bds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		bds.setUsername("watson");
		bds.setPassword("watson");
		bds.setInitialSize(30);
	}
}
