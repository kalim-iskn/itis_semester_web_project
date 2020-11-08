package ru.kpfu.itis.iskander.models;

import ru.kpfu.itis.iskander.helpers.CSRFTokenHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SetAttributesAnnouncementForm {

    public static void setAttributes(HttpServletRequest req, String name) throws IOException {
        req.setAttribute("type", name);
        req.setAttribute("csrf", CSRFTokenHelper.generate(req));
    }

}
