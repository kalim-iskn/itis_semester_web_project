package ru.kpfu.itis.iskander.servlets;

import ru.kpfu.itis.iskander.classes.SaleAnnouncement;
import ru.kpfu.itis.iskander.classes.Settings;
import ru.kpfu.itis.iskander.exceptions.ServerProblemException;
import ru.kpfu.itis.iskander.models.SearchAnnouncementGeneratorModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ArrayList<SaleAnnouncement> announcements = SearchAnnouncementGeneratorModel.getAnnouncements(req, 0);
            if (announcements == null)
                req.setAttribute("isNotFound", true);
            else
                req.setAttribute("announcements", announcements);

            Settings settings = new Settings();
            int searchOffset = Integer.parseInt(settings.get("search_offset"));
            req.setAttribute("searchOffset", searchOffset);

            getServletContext().getRequestDispatcher("/views/search.jsp").forward(req, resp);
        } catch (SQLException | ClassNotFoundException | ServerProblemException e) {
            getServletContext().getRequestDispatcher("/views/errors/errorPage.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            req.setAttribute("isNotFound", true);
            getServletContext().getRequestDispatcher("/views/search.jsp").forward(req, resp);
        }
    }

}
