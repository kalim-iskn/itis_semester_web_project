package ru.kpfu.itis.iskander.helpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

public class CSRFTokenHelper {

    public static String generate(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String token = getToken();
        session.setAttribute("_csrf", token);
        return token;
    }

    private static String getToken() {
        return UUID.randomUUID().toString();
    }

    public static boolean check(HttpServletRequest req, String token) {
        HttpSession session = req.getSession();
        String sessionToken = (String) session.getAttribute("_csrf");
        return sessionToken != null && sessionToken.equals(token);
    }

}
