package ru.kpfu.itis.iskander.servlets;

import ru.kpfu.itis.iskander.classes.PhotoRemover;
import ru.kpfu.itis.iskander.classes.SaleAnnouncement;
import ru.kpfu.itis.iskander.exceptions.NoSuchRecordIntoTableException;
import ru.kpfu.itis.iskander.repositories.SaleAnnouncementRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/main/announcement/delete")
public class DeleteAnnouncementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            SaleAnnouncementRepository repository = new SaleAnnouncementRepository();
            SaleAnnouncement announcement = repository.getById(id);
            String appPath = req.getServletContext().getRealPath("");
            if (repository.delete(announcement.getId(), (int) req.getAttribute("authorizedUserId"))) {
                PhotoRemover photoRemover = new PhotoRemover(appPath);
                photoRemover.deletePhoto(announcement.getMainPicture());
                for (String name : announcement.getPicturesArray()) {
                    photoRemover.deletePhoto(name);
                }
            }
            repository.closeConnection();
            resp.sendRedirect(req.getContextPath() + "/main");
        } catch (SQLException | ClassNotFoundException e) {
            getServletContext().getRequestDispatcher("/views/errors/errorPage.jsp").forward(req, resp);
        } catch (NoSuchRecordIntoTableException | NumberFormatException e) {
            getServletContext().getRequestDispatcher("/views/errors/notFoundPage.jsp").forward(req, resp);
        }
    }
}
