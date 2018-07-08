package ru.job4j.servlets;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test UsersServlet.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class UsersServletTest {

    private UsersServlet testServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private ValidateService validator = ValidateService.getInstance();

    @Before
    public void setUp() {
        this.testServlet = new UsersServlet();
        this.request = mock(HttpServletRequest.class);
        this.response = mock(HttpServletResponse.class);
        this.dispatcher = mock(RequestDispatcher.class);
    }

    @Test
    public void ifDeleteUserThenServletForwardAndSendMessage() throws ServletException, IOException {
        this.validator.add(new User("test", "test", "test", "user", "pass"));
        int userId = 0;
        for (User usr : this.validator.findAll()) {
            if (usr.getName().equals("test")) {
                userId = usr.getId();
            }
        }
        when(this.request.getParameter("id")).thenReturn(String.valueOf(userId));
        when(this.request.getRequestDispatcher("/WEB-INF/views/users.jsp")).thenReturn(this.dispatcher);
        this.testServlet.doPost(this.request, this.response);
        boolean result = true;
        for (User usr : this.validator.findAll()) {
            if (usr.getId() == userId) {
                result = false;
            }
        }
        assertTrue(result);
        verify(this.dispatcher).forward(this.request, this.response);
        verify(this.request).setAttribute("message", "User was deleted");
    }

    @Test
    public void ifDeleteNotUserThenServletForwardAndSendError() throws ServletException, IOException {
        when(this.request.getParameter("id")).thenReturn("-1");
        when(this.request.getRequestDispatcher("/WEB-INF/views/users.jsp")).thenReturn(this.dispatcher);
        this.testServlet.doPost(this.request, this.response);
        verify(this.dispatcher).forward(this.request, this.response);
        verify(this.request).setAttribute("error", "User not found");
    }
}