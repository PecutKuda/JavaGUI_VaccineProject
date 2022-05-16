import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class database {

	private static Connection con;
	public static Connection configDB() throws SQLException {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/puddingdb", "root", "");
		}
		catch (SQLException e) {
			System.out.println("Failed connect to database!" + e.getMessage());
		}
		return con;
	}

}
