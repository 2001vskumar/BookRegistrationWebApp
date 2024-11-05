package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet
{
	  private static final long serialVersionUID = 1L;

	    // SQL query to update the record in the database
	    public static final String query = "delete  from student  WHERE id=?";

	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        PrintWriter pw = resp.getWriter();
	        resp.setContentType("text/html");

	        int id = Integer.parseInt(req.getParameter("id"));

	        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	        String url = "jdbc:sqlserver://Satishkumar\\SQLEXPRESS;databaseName=rubeena;integratedSecurity=true;encrypt=false";

	        // Load JDBC driver
	        try {
	            Class.forName(driverName);
	        } catch (ClassNotFoundException cnf) {
	            cnf.printStackTrace();
	            pw.println("<h1>Error loading JDBC driver</h1>");
	            return;
	        }

	        // Update the record in the database
	        try (Connection con = DriverManager.getConnection(url);
	             PreparedStatement ps = con.prepareStatement(query)) {

	            ps.setInt(1, id);

	            int count = ps.executeUpdate();
	            if (count == 1) {
	                pw.println("<h2>Record Deleted Successfully</h2>");
	            } else {
	                pw.println("<h2>Record Not deleted for ID: " + id + "</h2>");
	            }
	        } catch (SQLException se) {
	            se.printStackTrace();
	            pw.println("<h1>SQL Error: " + se.getMessage() + "</h1>");
	        } catch (Exception e) {
	            e.printStackTrace();
	            pw.println("<h1>Error: " + e.getMessage() + "</h1>");
	        }

	        pw.println("<br><a href='bookList'>Book List</a>");
	    }

	    @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        // Call doPost to handle both GET and POST requests in the same way
	        doPost(req, resp);
	    }
}
