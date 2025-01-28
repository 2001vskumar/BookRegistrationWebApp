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
    private static final String query = "SELECT ID, BOOKNAME, BOOKEDITION, BOOKPRICE FROM BOOKDATA";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get PrintWriter to send response
        PrintWriter pw = res.getWriter();
        // Set content type
        res.setContentType("text/html");

        // Load JDBC driver
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
            pw.println("<h2 style='color:red;'>JDBC Driver Not Found!</h2>");
            return;
        }

        // Generate the connection
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true;databaseName=rubeena;encrypt=false";
        try (Connection con = DriverManager.getConnection(url); PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

            // Display book data in a table
            pw.println("<table border='1' align='center'>");
            pw.println("<tr><th>Book ID</th><th>Book Name</th><th>Book Edition</th><th>Book Price</th><th>Edit</th><th>Delete</th></tr>");
            while (rs.next()) {
                pw.println("<tr>");
                pw.println("<td>" + rs.getInt(1) + "</td>");
                pw.println("<td>" + rs.getString(2) + "</td>");
                pw.println("<td>" + rs.getString(3) + "</td>");
                pw.println("<td>" + rs.getFloat(4) + "</td>");
                pw.println("<td><a href='editScreen?id=" + rs.getInt(1) + "'>Edit</a></td>");
                pw.println("<td><a href='deleteurl?id=" + rs.getInt(1) + "'>Delete</a></td>");
                pw.println("</tr>");
            }
            pw.println("</table>");
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h2 style='color:red;'>Database Error: " + se.getMessage() + "</h2>");
        }

        // Add navigation link
        pw.println("<a href='home.html'>Home</a>");
    }
}
