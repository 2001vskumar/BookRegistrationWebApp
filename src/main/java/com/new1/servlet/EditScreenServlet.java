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

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public static final String query = "SELECT id, bookName, bookEdition, bookPrice FROM student WHERE id = ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html");
        int id = Integer.parseInt(req.getParameter("id"));

        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://Satishkumar\\SQLEXPRESS;databaseName=rubeena;integratedSecurity=true;encrypt=false";

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
            pw.println("<h1>Error loading JDBC driver</h1>");
            return;
        }

        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String bookName = rs.getString(2);
                    String bookEdition = rs.getString(3);
                    float bookPrice = rs.getFloat(4);

                    // Correct the form with proper input names and IDs
                    pw.println("<form action='editurl?id=" + id + "' method='POST'>");
                    pw.println("<table>");
                    pw.println("<tr><td>Book Name:</td><td><input type='text' name='bookName' value='" + bookName + "' required></td></tr>");
                    pw.println("<tr><td>Book Edition:</td><td><input type='text' name='bookEdition' value='" + bookEdition + "' required></td></tr>");
                    pw.println("<tr><td>Book Price:</td><td><input type='number' step='0.01' name='bookPrice' value='" + bookPrice + "' required></td></tr>");
                    pw.println("<tr><td colspan='2'><input type='submit' value='Update'></td></tr>");
                    pw.println("</table>");
                    pw.println("</form>");
                } else {
                    pw.println("<h1>No record found for ID: " + id + "</h1>");
                }
            }

        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>SQL Error: " + se.getMessage() + "</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>Error: " + e.getMessage() + "</h1>");
        }

        pw.println("<br><a href='home.html'>Home</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
