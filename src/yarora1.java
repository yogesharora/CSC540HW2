import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class yarora1 {

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
			System.out.println("Enter yor budget: ");
			int budget = inScanner.nextInt();
			System.out.println("Enter minimum memory: ");
			int minMemory = inScanner.nextInt();

			String query = "select p.model as pcModel, pr.model as printerModel"
				+" from printer pr, pc p"
				+" where (pr.price + p.price) <=" + budget 
				+" and p.ram>=" + minMemory 
				+" and pr.color='true' "
				+" and (pr.price + p.price) <=  (select min (p1.price + pr1.price)"  
				+" from printer pr1, pc p1"
				+" where pr1.color='true'"
				+" and p1.ram>= " + minMemory 
				+" )";
			rs = stmt.executeQuery(query);

			if (rs.next()) {
				System.out
						.println("Cheapest Available system with color printer in budget: ");
				String pcModel = rs.getString("pcModel");
				String printerModel = rs.getString("printerModel");
				System.out.println("PC" + "   " + "Printer");
				System.out.println(pcModel + "   " + printerModel);
				
			} else {
				query = "select p.model as pcModel, pr.model as printerModel"
					+" from printer pr, pc p"
					+" where (pr.price + p.price) <=" + budget 
					+" and p.ram>=" + minMemory 
					+" and pr.color='false' "
					+" and (pr.price + p.price) <=  (select min (p1.price + pr1.price)"  
					+" from printer pr1, pc p1"
					+" where pr1.color='false'"
					+" and p1.ram>= " + minMemory 
					+" )";

				rs = stmt.executeQuery(query);

				if (rs.next()) {
					System.out
							.println("Cheapest Available system with  with black and white printer in budget: ");
						String pcModel = rs.getString("pcModel");
						String printerModel = rs.getString("printerModel");
						System.out.println("PC" + "   " + "Printer");
						System.out.println(pcModel + "   " + printerModel);
				} else {
					System.out.println("No system available in budget");
				}
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
