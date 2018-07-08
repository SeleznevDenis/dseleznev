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
 * Test UserCreateServlet.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class UserCreateServletTest {

    private UserCreateServlet testServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private ValidateService validator = ValidateService.getInstance();

    @Before
    public void setUp() {
        this.testServlet = new UserCreateServlet();
        this.request = mock(HttpServletRequest.class);
        this.response = mock(HttpServletResponse.class);
        this.dispatcher = mock(RequestDispatcher.class);
    }

    @Test
    public void ifCreateUserThenValidateServiceReturnHim() throws ServletException, IOException {
        when(this.request.getParameter("name")).thenReturn("CreateTest");
        when(this.request.getParameter("login")).thenReturn("CreateTestLogin");
        when(this.request.getParameter("email")).thenReturn("testEmail");
        when(this.request.getParameter("role")).thenReturn("user");
        when(this.request.getParameter("password")).thenReturn("password");
        when(this.request.getRequestDispatcher("/WEB-INF/views/create_user.jsp")).thenReturn(this.dispatcher);
        this.testServlet.doPost(this.request, this.response);
        boolean createResult = false;
        int userId = 0;
        for (User usr : this.validator.findAll()) {
            if (usr.getName().equals("CreateTest")
                    && usr.getLogin().equals("CreateTestLogin")
                    && usr.getEmail().equals("testEmail")
                    && usr.getPassword().equals("password")) {
                createResult = true;
                userId = usr.getId();
            }
        }
        this.validator.delete(userId);
        assertTrue(createResult);
        verify(this.request).setAttribute("message", "User was added");
        verify(this.dispatcher).forward(request, response);
    }

    @Test
    public void ifCreateUserWithInvalidParametersThenServletReturnErrorText() throws ServletException, IOException {
        String userName = "testInvalidInputUser";
        when(this.request.getParameter("name")).thenReturn(userName);
        when(this.request.getRequestDispatcher("/WEB-INF/views/create_user.jsp")).thenReturn(this.dispatcher);
        this.testServlet.doPost(this.request, this.response);
        verify(this.request).setAttribute("error", "Incorrect input data, please try again.");
        verify(this.dispatcher).forward(this.request, this.response);
        for (User usr : this.validator.findAll()) {
            assertNotEquals(usr.getName(), userName);
        }
    }
}