package ru.job4j.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Отображает таблицу пользователей.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class UsersServlet extends HttpServlet {
    /**
     * Хранит ссылку на объект ValidateService.
     */
    private final ValidateService validator = ValidateService.getInstance();

    /**
     * Отображает страницу с таблицей пользователей и кнопками редактирования и удаления.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(getUserList(req.getContextPath(), ""));
        writer.flush();
    }

    /**
     * Удаляет пользователя из системы.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String deletingUserId = req.getParameter("id");
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        if (deletingUserId != null) {
            String message;
            if (this.validator.delete(Integer.parseInt(deletingUserId))) {
                message = "User was deleted";
            } else {
                message = "User not found";
            }
            writer.append(getUserList(req.getContextPath(), message));
            writer.flush();
        }
    }

    /**
     * Возвращает строку содержащую код таблицы с пользователями.
     * @param contextPath путь к корню сайта.
     * @param message сообщение для отображения на странице.
     * @return String
     */
    private String getUserList(String contextPath, String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>"
                + "<html lang='en'>"
                + "<head>"
                + "    <meta charset='UTF-8'>"
                + "    <title>All Users</title>"
                + "</head>"
                + "<body>"
                + "<table>"
                + "    <caption>All Users</caption>"
                + "    <tr>"
                + "        <th>Id</th>"
                + "        <th>Name</th>"
                + "        <th>Login</th>"
                + "        <th>Email</th>"
                + "    </tr>");
        for (User usr : this.validator.findAll()) {
            int userId = usr.getId();
            sb.append("<tr>"
                    + " <td>" + userId + "</td>"
                    + " <td>" + usr.getName() + "</td>"
                    + " <td>" + usr.getLogin() + "</td>"
                    + " <td>" + usr.getEmail() + "</td>"
                    + " <td>"
                    + "     <form action='" + contextPath + "/edit' method='get'>"
                    + "         <input type='hidden' name='id' value='" + userId + "'/>"
                    + "         <input type='submit' value='Edit'/>"
                    + "     </form>"
                    + " </td>"
                    + " <td>"
                    + "    <form action='" + contextPath + "/list' method='post'>"
                    + "        <input type='hidden' name='id' value='" + userId + "'/>"
                    + "        <input type='submit' value='Delete'/>"
                    + "    </form>"
                    + " </td>"
                    + "</tr>"
            );
        }
        sb.append("</form>"
                + message
                + "</body>"
                + "</html>"
        );
        return sb.toString();
    }
}
