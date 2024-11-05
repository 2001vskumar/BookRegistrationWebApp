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
    public static final String query = "insert into student(bookName,bookEdition,bookPrice) values(?,?,?)";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = resp.getWriter();
        // set content type
        resp.setContentType("text/html");

        String bookName = req.getParameter("bookName");
        String bookEdition = req.getParameter("bookEdition");
        float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));

        // load JDBC driver
        String drivername = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://Satishkumar\\SQLEXPRESS;databaseName=rubeena;integratedSecurity=true;encrypt=false";

        try {
            Class.forName(drivername);
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        // generate the connection
        try(Connection con = DriverManager.getConnection(url);
             PreparedStatement ps = con.prepareStatement(query)) {
             
            
            ps.setString(1, bookName);
            ps.setString(2, bookEdition);
            ps.setFloat(3, bookPrice);

            int count = ps.executeUpdate();
            if (count == 1) {
                pw.println("<h2>Record is Registered Successfully</h2>");
            } else {
                pw.println("<h2>Record is not Registered Successfully");
            }
           
        }
     
        catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h2>");  // Corrected closing tag
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h2>");  // Corrected closing tag
        }
        pw.println(" <a href='home.html'>Home</a>");
        pw.println("<br>");
        pw.println(" <a href='bookList'>Book List</a>");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
