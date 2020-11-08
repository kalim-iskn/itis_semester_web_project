package ru.kpfu.itis.iskander.servlets;

import ru.kpfu.itis.iskander.classes.SaleAnnouncement;
import ru.kpfu.itis.iskander.repositories.SaleAnnouncementRepository;

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
            String[] fields = new String[]{"q", "searchCity", "searchCategory", "price_from", "price_to"};
            for (String field : fields) {
                String val = req.getParameter(field) == null ? "" : req.getParameter(field);
                req.setAttribute(field, val);
            }

            SaleAnnouncementRepository repository = new SaleAnnouncementRepository();
            String priceFrom = (String) req.getAttribute("price_from");
            String priceTo = (String) req.getAttribute("price_to");
            int[] prices = new int[]{
                    priceFrom.equals("") ? -1 : Integer.parseInt(priceFrom),
                    priceTo.equals("") ? -1 : Integer.parseInt(priceTo)
            };
            ArrayList<SaleAnnouncement> announcements = repository.getWithParameters(
                    (String) req.getAttribute("q"), (String) req.getAttribute("searchCity"),
                    (String) req.getAttribute("searchCategory"), prices
            );

            if (announcements == null)
                req.setAttribute("isNotFound", true);
            else
                req.setAttribute("announcements", announcements);

            getServletContext().getRequestDispatcher("/views/search.jsp").forward(req, resp);
        } catch (SQLException | ClassNotFoundException e) {
            getServletContext().getRequestDispatcher("/views/errors/errorPage.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            req.setAttribute("isNotFound", true);
            getServletContext().getRequestDispatcher("/views/search.jsp").forward(req, resp);
        }
    }

}
