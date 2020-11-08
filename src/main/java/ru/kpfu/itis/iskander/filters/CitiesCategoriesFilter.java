package ru.kpfu.itis.iskander.filters;

import ru.kpfu.itis.iskander.exceptions.ServerProblemException;
import ru.kpfu.itis.iskander.helpers.ListHelper;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.Filter;

public class CitiesCategoriesFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, javax.servlet.FilterChain chain) throws ServletException, java.io.IOException {
        try {
            ListHelper cityHelper = new ListHelper("cities");
            request.setAttribute("cities", cityHelper.getList());

            ListHelper categoriesHelper = new ListHelper("categories");
            request.setAttribute("categories", categoriesHelper.getList());

            chain.doFilter(request, response);
        } catch (ServerProblemException e) {
            request.getRequestDispatcher("/views/errors/errorPage.jsp").forward(request, response);
        }
    }

}
