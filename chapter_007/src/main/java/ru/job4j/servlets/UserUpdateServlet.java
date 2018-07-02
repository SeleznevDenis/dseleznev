package ru.job4j.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Сервлет, обновляющий данные пользователя.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class UserUpdateServlet extends HttpServlet {

    /**
     * Ссылка на объект ValidateService.
     */
    private final ValidateService validator = ValidateService.getInstance();

    /**
     * Отображает на странице форму для редактирования пользователя с заполненными полями.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int updatedUserId = Integer.parseInt(req.getParameter("id"));
        final User updatedUser = this.validator.findById(updatedUserId);
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        if (updatedUser != null) {
            writer.print("<!DOCTYPE html>"
                    + "<html lang='en'>"
                    + "<head>"
                    + "    <meta charset='UTF-8'>"
                    + "    <title>Create user</title>"
                    + "</head>"
                    + "<body>"
                    + "<form action ='" + req.getContextPath() + "/edit' method='post'>"
                    + "    Login:<input type='text' name='login' value='" + updatedUser.getLogin() + "'/><br>"
                    + "    Name:<input type='text' name='name' value='" + updatedUser.getName() + "'/><br>"
                    + "    Email:<input type='text' name='email' value='" + updatedUser.getEmail() + "'/><br>"
                    + "          <input type='hidden' name='id' value='" + updatedUserId + "'/>"
                    + "    <input type='submit' >"
                    + "</form>"
                    + "</body>"
                    + "</html>"
            );
        } else {
            writer.print("User nor found");
        }
        writer.flush();
    }

    /**
     * Производит редактирование пользователя
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User updatedUser = new User(
                req.getParameter("name"),
                req.getParameter("login"),
                req.getParameter("email")
        );
        updatedUser.setId(Integer.parseInt(req.getParameter("id")));
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        if (this.validator.update(updatedUser)) {
            writer.append("User successfully updated");
        } else {
            writer.append("User don't found");
        }
        writer.flush();
    }
}
