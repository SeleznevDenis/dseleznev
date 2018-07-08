package ru.job4j.servlets;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Calendar;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test UserUpdateServlet.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class UserUpdateServletTest {

    private UserUpdateServlet testServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    @Before
    public void setUp() {
        this.testServlet = new UserUpdateServlet();
        this.request = mock(HttpServletRequest.class);
        this.response = mock(HttpServletResponse.class);
        this.dispatcher = mock(RequestDispatcher.class);
    }

    private ValidateService validator = ValidateService.getInstance();

    @Test
    public void ifUpUserThenUserShouldBeUpdate() throws ServletException, IOException {
        this.validator.add(new User("updateTest", "login", "email", "administrator", "test"));
        int userId = 0;
        Calendar userDate = null;
        for (User user : this.validator.findAll()) {
            if (user.getName().equals("updateTest")) {
                userId = user.getId();
                userDate = user.getCreateDate();
            }
        }
        User expect = new User("testName", "testLogin", "testMail", "user", "pass");
        expect.setId(userId);
        expect.setCreateDate(userDate);
        when(this.request.getParameter("id")).thenReturn(String.valueOf(userId));
        when(this.request.getParameter("name")).thenReturn(expect.getName());
        when(this.request.getParameter("login")).thenReturn(expect.getLogin());
        when(this.request.getParameter("email")).thenReturn(expect.getEmail());
        when(this.request.getParameter("role")).thenReturn(expect.getRole());
        when(this.request.getParameter("password")).thenReturn(expect.getPassword());
        when(this.request.getRequestDispatcher("/WEB-INF/views/users.jsp")).thenReturn(dispatcher);
        this.testServlet.doPost(this.request, this.response);
        User afterUp = this.validator.findById(userId);
        verify(this.dispatcher).forward(this.request, this.response);
        verify(this.request).setAttribute("message", "User successfully updated");
        assertThat(afterUp, is(expect));
    }

    @Test
    public void ifUpNotExistentUserThenServletShouldBeForwardWithMessage() throws ServletException, IOException {
        when(this.request.getParameter("id")).thenReturn("0");
        when(this.request.getRequestDispatcher("/WEB-INF/views/users.jsp")).thenReturn(this.dispatcher);
        this.testServlet.doPost(this.request, this.response);
        verify(this.dispatcher).forward(this.request, this.response);
        verify(this.request).setAttribute("error", "User not updated");
    }
}