import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBWriter {
	private static Connection conn= null;
	public Connection getConnection(){
		String driverName = "oracle.jdbc.driver.OracleDriver";
		try {
		if (conn!=null&&(!conn.isClosed()))
			return conn;
			else{
			Properties prop = new Properties();
				prop.load(new FileInputStream("config.properties"));
					Class.forName(driverName);
					   conn = DriverManager.getConnection("jdbc:oracle:thin:@"+prop.getProperty("hostname")+":"+prop.getProperty("port")+"/"+prop.getProperty("servicename"), prop.getProperty("dbuser"), prop.getProperty("dbpassword"));

		}			
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return conn;
	}


}
