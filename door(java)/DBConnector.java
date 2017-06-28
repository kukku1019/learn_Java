/**
 *
 */
package door;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

	private static String driveName = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost/xxxxxpi";
	private static String user = "xxxx";
	private static String password = "xxxx";

	Log4j log = new Log4j(new Object(){}.getClass().getName());

	public Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(driveName);
			con = (Connection) DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			log.write(e, 2);
		} catch (SQLException e) {
			log.write(e, 2);
		}
		return con;
	}
}
