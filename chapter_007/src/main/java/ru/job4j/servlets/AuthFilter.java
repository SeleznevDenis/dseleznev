package ru.job4j.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Фильтр, проверяет авторизован ли пользователь.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class AuthFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger("servlets");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    /**
     * При заходе на любую страницу, проверяет авторизован ли пользователь, если нет
     * перенаправляет его на страницу авторизации.
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        try {
            if (!request.getRequestURI().contains("/signin") && session.getAttribute("loginRole") == null) {
                ((HttpServletResponse) resp).sendRedirect(String.format("%s/signin", request.getContextPath()));
            } else {
                chain.doFilter(req, resp);
            }
        } catch (ServletException | IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
