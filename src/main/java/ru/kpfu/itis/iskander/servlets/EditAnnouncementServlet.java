package ru.kpfu.itis.iskander.servlets;

import ru.kpfu.itis.iskander.classes.SaleAnnouncement;
import ru.kpfu.itis.iskander.exceptions.NoSuchRecordIntoTableException;
import ru.kpfu.itis.iskander.helpers.CSRFTokenHelper;
import ru.kpfu.itis.iskander.models.SetAttributesAnnouncementForm;
import ru.kpfu.itis.iskander.models.UpdateAddAnnouncementModel;
import ru.kpfu.itis.iskander.repositories.SaleAnnouncementRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/main/announcement/edit")
@MultipartConfig
public class EditAnnouncementServlet extends HttpServlet {

    private final String pageName = "Редактирование объявления";
    private SaleAnnouncement saleAnnouncement;
    private SaleAnnouncementRepository repository;

    private boolean initialize(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            repository = new SaleAnnouncementRepository();
            saleAnnouncement = repository.getById(id);
            repository.closeConnection();
            if (saleAnnouncement.getAuthorId() != (int) req.getAttribute("authorizedUserId")) {
                resp.sendRedirect(req.getContextPath() + "/main");
                return false;
            }
            req.setAttribute("name", saleAnnouncement.getName());
            req.setAttribute("description", saleAnnouncement.getDescription());
            req.setAttribute("city", saleAnnouncement.getCity());
            req.setAttribute("category", saleAnnouncement.getCategory());
            req.setAttribute("is_new", saleAnnouncement.isNew() ? 1 : 0);
            req.setAttribute("main_picture", saleAnnouncement.getMainPicture());
            req.setAttribute("pictures", saleAnnouncement.getPicturesArray());
            req.setAttribute("price", saleAnnouncement.getPrice());
            req.setAttribute("is_edit", true);
            return true;
        } catch (NumberFormatException | NoSuchRecordIntoTableException | SQLException | ClassNotFoundException e) {
            resp.sendRedirect(req.getContextPath() + "/main");
            return false;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (initialize(req, resp)) {
            SetAttributesAnnouncementForm.setAttributes(req, pageName);
            getServletContext().getRequestDispatcher("/views/announcementEditAdd.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!CSRFTokenHelper.check(req, req.getParameter("_csrf"))) {
            resp.sendRedirect(req.getContextPath() + "/main/announcement/edit?id=" + req.getParameter("id"));
        } else {
            ArrayList<String> errors = UpdateAddAnnouncementModel.execute(req, saleAnnouncement.getId(), saleAnnouncement.getPicturesArray().length);
            if (!errors.isEmpty()) {
                req.setAttribute("errors", errors);
                req.setAttribute("isErrors", true);
                String[] fields = new String[]{"name", "description", "city", "category", "is_new"};
                for (String field : fields)
                    req.setAttribute(field, req.getParameter(field));
            } else {
                req.setAttribute("isSuccess", true);
                req.setAttribute("successText", "Объявление успешно отредактировано");
            }
            doGet(req, resp);

        }
    }

}
