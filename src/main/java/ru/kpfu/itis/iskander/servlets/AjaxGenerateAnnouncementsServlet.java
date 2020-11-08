package ru.kpfu.itis.iskander.servlets;

import ru.kpfu.itis.iskander.classes.SaleAnnouncement;
import ru.kpfu.itis.iskander.exceptions.ServerProblemException;
import ru.kpfu.itis.iskander.models.SearchAnnouncementGeneratorModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/get_announcements")
public class AjaxGenerateAnnouncementsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        try {
            int offset = Integer.parseInt(req.getParameter("offset"));
            ArrayList<SaleAnnouncement> announcements = SearchAnnouncementGeneratorModel.getAnnouncements(req, offset);
            if (announcements == null) {
                out.print("not-found");
            } else {
                req.setAttribute("announcements", announcements);
                getServletContext().getRequestDispatcher("/views/announcements.jsp").forward(req, resp);
            }
        } catch (SQLException | ClassNotFoundException | NumberFormatException | ServerProblemException e) {
            out.print("error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/errors/notFoundPage.jsp").forward(req, resp);
    }
}
