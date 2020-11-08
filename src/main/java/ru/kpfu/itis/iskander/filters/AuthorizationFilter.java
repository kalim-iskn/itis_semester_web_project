package ru.kpfu.itis.iskander.filters;


import ru.kpfu.itis.iskander.exceptions.ServerProblemException;
import ru.kpfu.itis.iskander.models.CheckAuthorizationModel;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, javax.servlet.FilterChain chain) throws ServletException, java.io.IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String servletPath = httpRequest.getServletPath();

        int checkResult = 0;
        try {
            checkResult = CheckAuthorizationModel.check(httpRequest);
            if (checkResult != -1) {
                request.setAttribute("isAuthorize", true);
                request.setAttribute("authorizedUserId", checkResult);
                chain.doFilter(request, response);
            } else if (servletPath.startsWith("/main")){
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            } else {
                chain.doFilter(request, response);
            }
        } catch (ServerProblemException e) {
            request.getRequestDispatcher("/views/errors/errorPage.jsp").forward(httpRequest, httpResponse);
        }
    }

}
