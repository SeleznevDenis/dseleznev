package ru.job4j.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Сервлет добавляющий пользователя в систему.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class UserCreateServlet extends HttpServlet {

    /**
     * Ссылка на объект ValidateService.
     */
    private final ValidateService validator = ValidateService.getInstance();

    /**
     * Отображает на странице форму для создания пользователя.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.print(this.getHtmlPage(req.getContextPath(), "Fill the form"));
        writer.flush();
    }

    /**
     * Возвращает HTML текст страницы ввода пользователя.
     * @param contextPatch адрес корня сайта.
     * @param message сообщение для отображения.
     * @return HTML текст в строке.
     */
    private String getHtmlPage(String contextPatch, String message) {
        return "<!DOCTYPE html>"
                + "<html lang='en'>"
                + "<head>"
                + "    <meta charset='UTF-8'>"
                + "    <title>Create user</title>"
                + "</head>"
                + "<body>"
                + message
                + "<form action ='" + contextPatch + "/create' method='post'>"
                + "    Login:<input type='text' name='login'/><br>"
                + "    Name:<input type='text' name='name'/><br>"
                + "    Email:<input type='text' name='email'/><br>"
                + "    <input type='submit' >"
                + "</form>"
                + "</body>"
                + "</html>";
    }

    /**
     * Выполняет добавление пользователя в систему.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        resp.setContentType("text/html");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        if (this.validator.add(new User(name, login, email))) {
            writer.append(this.getHtmlPage(req.getContextPath(), "User was added"));
        } else {
            writer.append(this.getHtmlPage(req.getContextPath(), "Incorrect input data, please try again."));
        }
        writer.flush();
    }
}
