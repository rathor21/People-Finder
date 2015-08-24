import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Extend HttpServlet class
@WebServlet("/PeopleFinder")
public class PeopleFinder extends HttpServlet {
	static Connection conn = null;

	

	public void init() throws ServletException {
		// Do required initialization
		super.init();
		openConnection();

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String message = "";
		String message2 = "";
		String search = "";
		String firstName = "";
		String lastName = "";
		String email = "";
		String company = "";
		String company2 = "";
		String firstName2 = "";
		String lastName2 = "";
		String email2 = "";
		ArrayList<String> firstNameList = new ArrayList<String>();
		ArrayList<String> lastNameList = new ArrayList<String>();
		ArrayList<String> emailList = new ArrayList<String>();
		ArrayList<String> companyList = new ArrayList<String>();
		ArrayList<String> company2List = new ArrayList<String>();
		ArrayList<String> firstName2List = new ArrayList<String>();
		ArrayList<String> lastName2List = new ArrayList<String>();
		ArrayList<String> email2List = new ArrayList<String>();
		String button = request.getParameter("button");
		// System.out.println(button);
		if (button == null) {
		} else {
			search = request.getParameter("search");
			ResultSet result;
			String sql = "SELECT firstname,lastname,emailaddress, company from CUSTOMER where lastname like '"
					+ search + "%'";
			System.out.println(sql);
			try {
				result = getFromDB(sql);
				while (result.next()) {
					firstName = result.getString("firstname");
					lastName = result.getString("lastname");
					email = result.getString("emailaddress");
					company = result.getString("company");
					firstNameList.add(firstName);
					lastNameList.add(lastName);
					emailList.add(email);
					companyList.add(company);

				}

				message += " <table class= \"table\"><thead><tr><th>First Name</th><th>Last Name</th><th>Email</th><th>Company</th></tr></thead><tbody>";

				for (int i = 0; i < firstNameList.size(); i++) {
					firstName = firstNameList.get(i);
					lastName = lastNameList.get(i);
					email = emailList.get(i);
					company = companyList.get(i);
					message += "<tr><td>" + firstName + "</td><td>" + lastName
							+ "</td><td>" + email + "</td><td>" + company + "</td><td>";

				}
				message += "</tbody></table></div>";
				
				ResultSet result2;
				String sql2 = "SELECT firstname,lastname,emailaddress, company from CUSTOMER where company like '"
						+ search + "%'";
				System.out.println(sql2);
	
					result2 = getFromDB(sql2);
					while (result2.next()) {
						firstName2 = result2.getString("firstname");
						lastName2 = result2.getString("lastname");
						email2 = result2.getString("emailaddress");
						company2 = result2.getString("company");
						firstName2List.add(firstName2);
						lastName2List.add(lastName2);
						email2List.add(email2);
						company2List.add(company2);

					}

					message2 += " <table class= \"table\"><thead><tr><th>First Name</th><th>Last Name</th><th>Email</th><th>Company</th></tr></thead><tbody>";

					for (int i = 0; i < firstName2List.size(); i++) {
						firstName2 = firstName2List.get(i);
						lastName2 = lastName2List.get(i);
						email2 = email2List.get(i);
						company2 = company2List.get(i);
						message2 += "<tr><td>" + firstName2 + "</td><td>" + lastName2
								+ "</td><td>" + email2 + "</td><td>" + company2 + "</td><td>";

					}
					message2 += "</tbody></table></div>";
					request.setAttribute("message", message);
				request.setAttribute("message2", message2);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		getServletContext().getRequestDispatcher("/peoplefinder.jsp").forward(
				request, response);
	}

	public void destroy() {
		// do nothing.
	}

	public static void openConnection() {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:testuser/password@localhost";
			Properties props = new Properties();
			props.setProperty("user", "testdb");
			props.setProperty("password", "password");
			conn = DriverManager.getConnection(url, props);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void updateDB(String sql) throws SQLException {

		PreparedStatement preStatement = conn.prepareStatement(sql);

		preStatement.setQueryTimeout(10);
		preStatement.executeUpdate();

	}

	public static ResultSet getFromDB(String sql) throws SQLException {

		PreparedStatement preStatement = conn.prepareStatement(sql);
		ResultSet result = preStatement.executeQuery();
		return result;
	}
}