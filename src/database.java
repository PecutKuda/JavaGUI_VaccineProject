import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

//Pengaplikasian Singleton dengan dibuatnya database.java yang tersedia untuk class-class lainnya
//secara khusus dan hanya untuk mengkoneksikan program ke database.
public class database {

	private static Connection con;
	public static Connection configDB() throws SQLException {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vaccinedb", "root", "");
		}
		catch (SQLException e) {
			System.out.println("Failed connect to database!" + e.getMessage());
		}
		return con;
	}

}
