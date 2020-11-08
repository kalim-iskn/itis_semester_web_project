package ru.kpfu.itis.iskander.servlets;

import ru.kpfu.itis.iskander.helpers.CSRFTokenHelper;
import ru.kpfu.itis.iskander.models.AuthorizationModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/login")
public class AuthorizationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("csrf", CSRFTokenHelper.generate(req));
        getServletContext().getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!CSRFTokenHelper.check(req, req.getParameter("_csrf"))) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            ArrayList<String> errors = AuthorizationModel.authenticate(req, resp);

            if (errors.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/main");
            } else {
                req.setAttribute("csrf", CSRFTokenHelper.generate(req));
                req.setAttribute("email", req.getParameter("email"));
                req.setAttribute("isErrors", true);
                req.setAttribute("errors", errors);
                getServletContext().getRequestDispatcher("/views/login.jsp").forward(req, resp);
            }
        }
    }

}
