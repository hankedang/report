package xreport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Test {

	public static final String url = "jdbc:mysql://centrixlink.mysql.rds.aliyuncs.com:3306/ad_stat";
	public static final String name = "com.mysql.jdbc.Driver";
	public static final String user = "stat";
	public static final String password = "Centrixlink123";
	
	public static String sql = "select * from test";

	public static void main(String[] args) throws Exception{
		Connection conn = null;
		PreparedStatement pst = null;
		Class.forName(name);
		conn = DriverManager.getConnection(url, user, password);
		pst = conn.prepareStatement(sql);
		
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()) {
			System.out.print(rs.getInt(1) + "\t");
			System.out.print(rs.getString(2)+ "\t");
			System.out.print(rs.getString(3)+ "\t");
			System.out.println();
		}
		
	}
	
	
	
	
	
}
