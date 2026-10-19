package com.codegym.controller;
import com.codegym.dao.UserDAO;
import com.codegym.model.User;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
@WebServlet(name = "UserServlet", urlPatterns = "/users")
public class UserServlet extends HttpServlet {
    private UserDAO userDAO;
    public void init() { userDAO = new UserDAO(); }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("create".equals(action)) {
            try {
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String country = request.getParameter("country");
                userDAO.insertUserStore(new User(name, email, country));
                response.sendRedirect("users");
            } catch (SQLException e) { throw new ServletException(e); }
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            User existingUser = userDAO.getUserById(id);
            request.setAttribute("user", existingUser);
            request.getRequestDispatcher("user/edit.jsp").forward(request, response);
        } else {
            request.setAttribute("listUser", userDAO.selectAllUsers());
            request.getRequestDispatcher("user/list.jsp").forward(request, response);
        }
    }
}
