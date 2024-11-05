package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/bookList")
public class BookListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Query to fetch data from the student table
    public static final String query = "SELECT id, bookName, bookEdition, bookPrice FROM student";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get PrintWriter to write HTML response
        PrintWriter pw = resp.getWriter();
        // Set content type
        resp.setContentType("text/html");

        // JDBC connection parameters
        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://Satishkumar\\SQLEXPRESS;databaseName=rubeena;integratedSecurity=true;encrypt=false";

        // Load JDBC driver
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
            pw.println("<h1>Error loading JDBC driver</h1>");
            return;  // Exit if the driver is not found
        }

        // Generate the connection and execute query
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) { // Move ResultSet inside try-with-resources

            // Start HTML table
            pw.println("<table border='1' cellpadding='10'>");
            pw.println("<tr>");
            pw.println("<th>ID</th>");
            pw.println("<th>Book Name</th>");
            pw.println("<th>Book Edition</th>");
            pw.println("<th>Book Price</th>");
            pw.println("<th>Edit</th>");
            pw.println("<th>Delete</th>");
            pw.println("</tr>");

            // Iterate through the result set and print the data in the table
            while (rs.next()) {
                pw.println("<tr>");
                pw.println("<td>" + rs.getInt(1) + "</td>");   // ID
                pw.println("<td>" + rs.getString(2) + "</td>"); // Book Name
                pw.println("<td>" + rs.getString(3) + "</td>"); // Book Edition
                pw.println("<td>" + rs.getFloat(4) + "</td>");  // Book Price
                pw.println("<td><a href='editScreen?id="+rs.getInt(1)+"'>Edit</a></td>");
                pw.println("<td><a href='deleteurl?id="+rs.getInt(1)+"'>Delete</a></td>");
                pw.println("</tr>");
            }

            pw.println("</table>");
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>SQL Error: " + se.getMessage() + "</h1>");  // Corrected closing tag
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>Error: " + e.getMessage() + "</h1>");  // Corrected closing tag
        }

        // Provide a link to go back to the home page
        pw.println("<br><a href='home.html'>Home</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);  // Handle POST requests as GET requests
    }
}
