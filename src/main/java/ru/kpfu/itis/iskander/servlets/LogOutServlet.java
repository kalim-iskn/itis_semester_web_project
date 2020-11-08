package ru.kpfu.itis.iskander.servlets;

import ru.kpfu.itis.iskander.models.LogOutModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/main/logout")
public class LogOutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LogOutModel.logout(req, resp);
            resp.sendRedirect(req.getContextPath());
        } catch (SQLException | ClassNotFoundException e) {
            getServletContext().getRequestDispatcher("/views/errors/errorPage.jsp").forward(req, resp);
        }
    }

}
