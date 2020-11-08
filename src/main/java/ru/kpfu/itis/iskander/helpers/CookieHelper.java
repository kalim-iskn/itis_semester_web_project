package ru.kpfu.itis.iskander.helpers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieHelper {

    private final Cookie[] COOKIES;

    public CookieHelper(HttpServletRequest request) {
        COOKIES = request.getCookies();
    }

    public Cookie search(String name) {
        if (COOKIES != null) {
            for (Cookie cookie : COOKIES) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public Cookie searchInt(String name) {
        Cookie result = search(name);
        if (result == null)
            return null;
        try {
            Integer.parseInt(result.getValue());
            return result;
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
