package ru.kpfu.itis.iskander.models;

import ru.kpfu.itis.iskander.classes.SaleAnnouncement;
import ru.kpfu.itis.iskander.exceptions.ServerProblemException;
import ru.kpfu.itis.iskander.repositories.SaleAnnouncementRepository;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchAnnouncementGeneratorModel {

    public static ArrayList<SaleAnnouncement> getAnnouncements(HttpServletRequest req, int offset) throws SQLException, ClassNotFoundException, NumberFormatException, ServerProblemException {
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

        return repository.getWithParameters(
                (String) req.getAttribute("q"), (String) req.getAttribute("searchCity"),
                (String) req.getAttribute("searchCategory"), prices, offset
        );
    }

}
