package ru.kpfu.itis.iskander.servlets;

import ru.kpfu.itis.iskander.classes.SaleAnnouncement;
import ru.kpfu.itis.iskander.classes.Settings;
import ru.kpfu.itis.iskander.exceptions.ServerProblemException;
import ru.kpfu.itis.iskander.repositories.SaleAnnouncementRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Settings settings = new Settings();
            String[] categories = settings.getArray("categories_in_home_page");

            SaleAnnouncementRepository repository = new SaleAnnouncementRepository();
            HashMap<String, ArrayList<SaleAnnouncement>> announcements = new HashMap<>();
            for (String category: categories) {
                announcements.put(category, repository.getFromCategory(category));
            }
            req.setAttribute("announcements", announcements);
            getServletContext().getRequestDispatcher("/views/home.jsp").forward(req, resp);
        } catch (SQLException | ClassNotFoundException | ServerProblemException e) {
            getServletContext().getRequestDispatcher("/views/errors/errorPage.jsp").forward(req, resp);
        }
    }
}
