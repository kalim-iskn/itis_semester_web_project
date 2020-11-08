package ru.kpfu.itis.iskander.servlets;

import ru.kpfu.itis.iskander.classes.SaleAnnouncement;
import ru.kpfu.itis.iskander.classes.User;
import ru.kpfu.itis.iskander.exceptions.NoSuchRecordIntoTableException;
import ru.kpfu.itis.iskander.repositories.SaleAnnouncementRepository;
import ru.kpfu.itis.iskander.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/announcement")
public class AnnouncementPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            SaleAnnouncementRepository repository = new SaleAnnouncementRepository();
            SaleAnnouncement announcement = repository.getById(id);
            repository.closeConnection();

            UserRepository userRepository = new UserRepository();
            User user = userRepository.getById(announcement.getAuthorId());
            userRepository.closeConnection();

            req.setAttribute("announcement", announcement);
            req.setAttribute("user", user);
            req.setAttribute("isHasExtraPictures",
                    announcement.getPicturesArray().length > 0 && !announcement.getPicturesArray()[0].equals(""));

            getServletContext().getRequestDispatcher("/views/announcementPage.jsp").forward(req, resp);
        } catch (SQLException | ClassNotFoundException e) {
            getServletContext().getRequestDispatcher("/views/errors/errorPage.jsp").forward(req, resp);
        } catch (NoSuchRecordIntoTableException | NumberFormatException e) {
            getServletContext().getRequestDispatcher("/views/errors/notFoundPage.jsp").forward(req, resp);
        }
    }

}
