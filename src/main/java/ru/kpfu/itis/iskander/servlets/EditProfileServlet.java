package ru.kpfu.itis.iskander.servlets;

import ru.kpfu.itis.iskander.classes.User;
import ru.kpfu.itis.iskander.exceptions.NoSuchRecordIntoTableException;
import ru.kpfu.itis.iskander.helpers.CSRFTokenHelper;
import ru.kpfu.itis.iskander.helpers.ListHelper;
import ru.kpfu.itis.iskander.repositories.UserRepository;
import ru.kpfu.itis.iskander.models.EditProfileModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/main/edit")
public class EditProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = (int) req.getAttribute("authorizedUserId");
            UserRepository userRepository = new UserRepository();
            User user = userRepository.getById(id);
            userRepository.closeConnection();

            req.setAttribute("user", user);
            req.setAttribute("csrf", CSRFTokenHelper.generate(req));

            getServletContext().getRequestDispatcher("/views/editProfile.jsp").forward(req, resp);
        } catch (SQLException | ClassNotFoundException | NoSuchRecordIntoTableException | FileNotFoundException e) {
            getServletContext().getRequestDispatcher("/views/errors/errorPage.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!CSRFTokenHelper.check(req, req.getParameter("_csrf"))) {
            resp.sendRedirect(req.getContextPath() + "/main/edit");
        } else {
            try {
                ArrayList<String> errors = EditProfileModel.edit(req);
                if (!errors.isEmpty()) {
                    req.setAttribute("errors", errors);
                    req.setAttribute("isErrors", true);
                } else {
                    req.setAttribute("isSuccess", true);
                }
                doGet(req, resp);
            } catch (SQLException | ClassNotFoundException | NoSuchRecordIntoTableException e) {
                getServletContext().getRequestDispatcher("/views/errors/errorPage.jsp").forward(req, resp);
            }
        }
    }

}
