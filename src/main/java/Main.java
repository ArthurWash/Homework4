

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.PrintWriter;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		int radio = -1;
		radio = Integer.parseInt(request.getParameter("rd"));
		String semesterSelect = "";
		String query = "";
		try {
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/NJIT?user=root&password=root");
			Statement stmt = con.createStatement();
			switch(radio) {
			case 0:{
				semesterSelect = request.getParameter("semesterSelect");
				
				if(semesterSelect.equals("Fall2021"))
				query = "Select * from Courses where semester = 'Fall2021'";
				else if(semesterSelect.equals("Spring2022"))
				query = "Select * from Courses where semester = 'Spring2022'";		
				
				ResultSet rs = stmt.executeQuery(query);
				out.println("<html>\n" + "<head><link rel=\"stylesheet\" href=\"style.css\">\n" + "<title></title></head>\n" + "<body>\n"
						+ "<br><br><br><br><br>"
						+ "<table align=\"center\" border=1 width=30% height=30%>");
						out.println("<tr><th>Course ID</th><th>Semester</th><th>Course Title</th><tr>");
				while(rs.next())
				{
					
					String courseID = rs.getString("courseID");
					String semester = rs.getString("semester");
					String courseTitle = rs.getString("courseTitle");
					out.println("<tr><td align=\"center\">" + courseID + "</td><td align=\"center\">" + semester + "</td><td align=\"center\">" + courseTitle + "</td></tr>");
				}
				out.println("</table>");
				out.println("<br><br><a href=\"index.html\"><button>Back</button></a>");
				out.println("</body>");
				out.println("</html>");
				
				stmt.close();
				break;
			}
			case 1:{
				String cID = request.getParameter("cID");
				String sem = request.getParameter("sem");
				
				if(!cID.equals("") && !sem.equals("")) {
					query = "Select * from Courses where semester = \'"+ sem +"\' and courseID=\'"+ cID +"\'";
				}
				
				ResultSet rs = stmt.executeQuery(query);
				
				while(rs.next()) {
					String c = rs.getString("courseTitle");
					out.println(
							"<html>\n" + "<head><link rel=\"stylesheet\" href=\"style.css\">\n" + "<title></title></head>\n" + "<body>\n"
							+ "<br><br><br><br><br>"
							+ "<h1>" + "You are registered in " + c + " for " + sem + "</h1>"
							+ "</table>"
							+ "<br><br><a href=\"index.html\"><button>Back</button></a>"
							+ "</body>"
							+ "</html>"
							);
				}	
			}
			}

		} catch (Exception ex) {
			out.println(
					"<html>\n" + "<head><link rel=\"stylesheet\" href=\"style.css\">\n" + "<title></title></head>\n" + "<body>\n"
					+ "<br><br><br><br><br>");
					if(radio==0)
					out.println(
					"<p>Error: Please select a semester</p>"
					+ "</table>"
					+ "<br><br><a href=\"index.html\"><button>Back</button></a>"
					+ "</body>"
					+ "</html>"
					);
					else
						out.println(
								"<h1>The course is not offered.</h1>"
								+ "</table>"
								+ "<br><br><a href=\"index.html\"><button>Back</button></a>"
								+ "</body>"
								+ "</html>"
								);
		}
		System.out.println("Program terminated with no error.");
	}
}
