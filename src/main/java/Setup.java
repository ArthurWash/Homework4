import java.sql.Connection;
import java.sql.DriverManager;

public class Setup {
  public static void main(String[] args) {
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost/NJIT?user=root&password=root");
    } catch (Exception ex) {
      System.out.println("LocalizedMessage: " +
	  ex.getLocalizedMessage());
      System.out.println("Message: " + ex.getMessage());
      System.out.println("Cause: " + ex.getCause());
      System.out.println("Class: " + ex.getClass());
      System.out.println("StackTrace: " + ex.getStackTrace());
      System.exit(0);
    }
    System.out.println("Program terminated with no error.");
  }
}
