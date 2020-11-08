package ru.kpfu.itis.iskander.servlets;

import ru.kpfu.itis.iskander.helpers.CSRFTokenHelper;
import ru.kpfu.itis.iskander.models.SetAttributesAnnouncementForm;
import ru.kpfu.itis.iskander.models.UpdateAddAnnouncementModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/main/announcement/add")
@MultipartConfig
public class AddAnnouncementServlet extends HttpServlet {

    private final String pageName = "Добавление нового объявления";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SetAttributesAnnouncementForm.setAttributes(req, pageName);

        getServletContext().getRequestDispatcher("/views/announcementEditAdd.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!CSRFTokenHelper.check(req, req.getParameter("_csrf"))) {
            resp.sendRedirect(req.getContextPath() + "/main/add");
        } else {
            ArrayList<String> errors = UpdateAddAnnouncementModel.execute(req, -1, 0);
            if (!errors.isEmpty()) {
                req.setAttribute("errors", errors);
                req.setAttribute("isErrors", true);
                String[] fields = new String[]{"name", "description", "city", "category", "is_new", "price"};
                for (String field : fields)
                    req.setAttribute(field, req.getParameter(field));
            } else {
                req.setAttribute("isSuccess", true);
                req.setAttribute("successText", "Объявление успешно добавлено");
            }

            SetAttributesAnnouncementForm.setAttributes(req, pageName);
            getServletContext().getRequestDispatcher("/views/announcementEditAdd.jsp").forward(req, resp);
        }
    }
}
