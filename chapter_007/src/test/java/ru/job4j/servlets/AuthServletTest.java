package ru.job4j.servlets;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * Test AuthServlet.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class AuthServletTest {
    private AuthServlet testServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private ValidateService validator = ValidateService.getInstance();

    @Before
    public void setUp() {
        this.testServlet = new AuthServlet();
        this.request = mock(HttpServletRequest.class);
        this.response = mock(HttpServletResponse.class);
        this.dispatcher = mock(RequestDispatcher.class);
    }

    @Test
    public void ifLoginRootThenSessionShouldBeSetAttributeLoginRole() throws IOException {
        HttpSession session = mock(HttpSession.class);
        when(this.request.getSession()).thenReturn(session);
        when(this.request.getParameter("action")).thenReturn("signin");
        when(this.request.getParameter("login")).thenReturn("root");
        when(this.request.getParameter("password")).thenReturn("root");
        when(this.request.getRequestDispatcher("/WEB-INF/views/auth.jsp")).thenReturn(this.dispatcher);
        User forMock = new User();
        forMock.setLogin("root");
        forMock.setRole("ADMINISTRATOR");
        testServlet.doPost(this.request, this.response);
        verify(session).setAttribute("loginRole", testServlet.new LoginRole(forMock));
        verify(this.response).sendRedirect(this.request.getContextPath() + "/list");
    }

    @Test
    public void ifSignInByNonExistentUserThenServletForwardRequest() throws ServletException, IOException {
        when(this.request.getParameter("action")).thenReturn("signin");
        when(this.request.getParameter("login")).thenReturn("nonExistentUsr");
        when(this.request.getParameter("password")).thenReturn("invalidPassword");
        when(this.request.getRequestDispatcher("/WEB-INF/views/auth.jsp")).thenReturn(this.dispatcher);
        testServlet.doPost(this.request, this.response);
        verify(this.request).setAttribute("error", "Invalid login or password");
        verify(this.dispatcher).forward(this.request, this.response);
    }

    @Test
    public void ifSiguOutThenServletSendRedirectAndInvalidateSession() throws IOException {
        HttpSession session = mock(HttpSession.class);
        when(this.request.getParameter("action")).thenReturn("signout");
        when(this.request.getSession()).thenReturn(session);
        testServlet.doPost(this.request, this.response);
        verify(this.response).sendRedirect(this.request.getContextPath() + "/signin");
        verify(session).invalidate();
    }
}