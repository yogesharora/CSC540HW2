import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class yarora2 {

	static final String jdbcURL = "jdbc:oracle:thin:@//ora.csc.ncsu.edu:1523/ORCL";

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

			String user = "yarora"; // For example, "jsmith"
			String passwd = "000920630"; // Your 9 digit student ID number

			conn = DriverManager.getConnection(jdbcURL, user, passwd);

			stmt = conn.createStatement();
			Scanner inScanner = new Scanner(System.in);
			System.out.println("Enter manufacturer: ");
			String manufacturer = inScanner.next();
			System.out.println("Enter model number: ");
			int modelNumber = inScanner.nextInt();
			System.out.println("Enter speed: ");
			float speed = inScanner.nextFloat();
			System.out.println("Enter ram: ");
			int ram = inScanner.nextInt();
			System.out.println("Enter hard disk size: ");
			int hd = inScanner.nextInt();
			System.out.println("Enter price: ");
			int price = inScanner.nextInt();
			
			String query = "select * from product where model=" + modelNumber; 

			rs = stmt.executeQuery(query);

			if (rs.next()) {
				System.out.println("PC with this model number exists");
			}
			else
			{
				StringWriter insert =  new StringWriter();
				PrintWriter ps=  new PrintWriter(insert);
				ps.format("insert into product values('%s', %d, 'pc')", manufacturer, modelNumber);
				stmt.executeUpdate(insert.toString());
				
				StringWriter insert2 =  new StringWriter();
				PrintWriter ps2=  new PrintWriter(insert2);
				ps2.format("insert into pc values(%d, %f, %d, %d, %d)", modelNumber, speed, ram, hd, price);
				stmt.executeUpdate(insert2.toString());
			}
		} catch (Throwable oops) {
			oops.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
			close(conn);
		}
	}

	static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Throwable whatever) {
			}
		}
	}

	static void close(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (Throwable whatever) {
			}
		}
	}

	static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Throwable whatever) {
			}
		}
	}
}
