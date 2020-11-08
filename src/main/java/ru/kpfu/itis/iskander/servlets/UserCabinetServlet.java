package ru.kpfu.itis.iskander.servlets;

import ru.kpfu.itis.iskander.classes.SaleAnnouncement;
import ru.kpfu.itis.iskander.helpers.CookieHelper;
import ru.kpfu.itis.iskander.repositories.SaleAnnouncementRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/main")
public class UserCabinetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int userId = (int) req.getAttribute("authorizedUserId");
            SaleAnnouncementRepository saleAnnouncementRepository = new SaleAnnouncementRepository();
            ArrayList<SaleAnnouncement> userAnnouncements = saleAnnouncementRepository.getAllByUser(userId);
            saleAnnouncementRepository.closeConnection();
            req.setAttribute("userAnnouncements", userAnnouncements);
            getServletContext().getRequestDispatcher("/views/cabinet.jsp").forward(req, resp);
        } catch (SQLException | ClassNotFoundException e) {
            getServletContext().getRequestDispatcher("/views/errors/errorPage.jsp").forward(req, resp);
        }
    }

}
