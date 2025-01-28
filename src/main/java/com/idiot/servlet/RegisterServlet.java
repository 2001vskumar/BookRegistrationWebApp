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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final String query = "INSERT INTO BOOKDATA(BOOKNAME, BOOKEDITION, BOOKPRICE) VALUES(?, ?, ?)";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get PrintWriter to send response
        PrintWriter pw = res.getWriter();
        // Set content type
        res.setContentType("text/html");

        // Get the book details from the form
        String bookName = req.getParameter("bookName");
        String bookEdition = req.getParameter("bookEdition");
        String bookPriceStr = req.getParameter("bookPrice");

        try {
            // Convert bookPrice to float
            float bookPrice = Float.parseFloat(bookPriceStr);

            // Load JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Generate the connection
            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;integratedSecurity=true;databaseName=rubeena;encrypt=false";
            try (Connection con = DriverManager.getConnection(url); PreparedStatement ps = con.prepareStatement(query)) {
                // Set parameters
                ps.setString(1, bookName);
                ps.setString(2, bookEdition);
                ps.setFloat(3, bookPrice);

                // Execute query
                int count = ps.executeUpdate();
                if (count == 1) {
                    pw.println("<h2 style='color:green;'>Record Registered Successfully!</h2>");
                } else {
                    pw.println("<h2 style='color:red;'>Record Registration Failed!</h2>");
                }
            }
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
            pw.println("<h2 style='color:red;'>JDBC Driver Not Found!</h2>");
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h2 style='color:red;'>Database Error: " + se.getMessage() + "</h2>");
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            pw.println("<h2 style='color:red;'>Invalid Price Format!</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h2 style='color:red;'>Unexpected Error: " + e.getMessage() + "</h2>");
        }

        // Add navigation links
        pw.println("<a href='home.html'>Home</a><br>");
        pw.println("<a href='bookList'>Book List</a>");
    }
}
