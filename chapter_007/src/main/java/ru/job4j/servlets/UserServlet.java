package ru.job4j.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.servlets.UserServlet.Message.Type;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Function;

/**
 * UserServlet.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 0.1
 */
public class UserServlet extends HttpServlet {
    private final static Logger LOG = LogManager.getLogger("servlets");

    /**
     * Ссылка на объект ValidateService.
     */
    private final ValidateService validator = ValidateService.getInstance();

    /**
     * Диспетчер dispatch.
     */
    private final DispatchPattern dispatch = new DispatchPattern();

    /**
     * Автоматически инициализирует диспетчер dispatch при загрузке сервлета.
     */
    @Override
    public void init() {
        this.dispatch.init();
    }

    /**
     * Обрабатывает запрос GET, возвращает страницу содержащую список всех пользователей.
     * @param req запрос.
     * @param resp ответ.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        try {
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            writer.append("All users:");
            for (User currentUser : this.validator.findAll()) {
                writer.println(String.format(
                        "Id: %s, Name: %s, Login: %s, Email: %s",
                        currentUser.getId(), currentUser.getName(), currentUser.getLogin(),
                        currentUser.getEmail()
                ));
            }
            writer.flush();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Обрабатывает запрос post. В зависимости от acton параметра запроса производит
     * Добавление, удаление, обновление пользователя в системе.
     * @param req запрос, содержит парамеры action, id, name, login, email.
     * @param resp ответ. Done - в случае успешного выполнения запроса
     *                    Action not performed - в случае когда действие выполнить неудалось.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Message msg = new Message(
                req.getParameter("action"),
                req.getParameter("id"),
                req.getParameter("name"),
                req.getParameter("login"),
                req.getParameter("email")
        );
        try {
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            resp.setContentType("text/html");
            if (this.dispatch.sent(msg)) {
                writer.append("Done");
            } else {
                writer.append("Action not performed");
            }
            writer.flush();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }

    }

    /**
     * Диспетчер. Обрабатывает запросы.
     * @author Denis Seleznev
     * @version $Id$
     * @since 0.1
     */
    public class DispatchPattern {
        /**
         * Хранилище типов запросов, и функций реагирующих на запрос.
         */
        private final Map<Message.Type, Function<Message, Boolean>> dispatch = new HashMap<>();

        /**
         * Добавляет пользователя в систему.
         * @return boolean успешность операции.
         */
        Function<Message, Boolean> addUser() {
            return message -> validator.add(new User(message));
        }

        /**
         * Обновляет пользователя в системе.
         * @return boolean успешность операции.
         */
        Function<Message, Boolean> updateUser() {
            return message -> validator.update(new User(message));
        }

        /**
         * Удаляет пользователя из системы.
         * @return boolean успешность операции.
         */
        Function<Message, Boolean> deleteUser() {
            return message -> validator.delete(Integer.parseInt(message.getId()));
        }

        /**
         * Загружает тип запроса и функцию обработки запроса.
         * @param type тип запроса.
         * @param handle функция обработки запроса.
         */
        void load(Type type, Function<Message, Boolean> handle) {
            this.dispatch.put(type, handle);
        }

        /**
         * Инициализирует диспетчер, загружает типы запросов и соответствующие им функции.
         */
        void init() {
            this.load(Type.ADD, this.addUser());
            this.load(Type.DELETE, this.deleteUser());
            this.load(Type.UPDATE, this.updateUser());
        }

        /**
         * Обрабатывает запрос.
         * @param msg запрос для обработки.
         * @return boolean успешность обработки запроса.
         */
        boolean sent(final Message msg) {
            return this.dispatch.get(msg.getType()).apply(msg);
        }
    }

    /**
     * Запрос.
     */
    static class Message {
        /**
         * Тип запроса.
         */
        private Type type;
        private String id;
        private String name;
        private String login;
        private String email;
        private Calendar createDate = new GregorianCalendar();

        /**
         * Инициализирует запрос. Автоматически задает дату создания пользователя,
         * @param type строка тип запроса.
         * @param id идентификатор пользователя.
         * @param name имя пользователя.
         * @param login логин пользователя
         * @param email электронная почта пользователя.
         */
        Message(String type, String id, String name, String login, String email) {
            this.type = Enum.valueOf(Type.class, type.toUpperCase());
            this.id = id;
            this.name = name;
            this.login = login;
            this.email = email;
            this.createDate.setTime(new Date());
        }

        /**
         * Перечисления возможных типов запросов.
         */
        enum Type {
            ADD,
            UPDATE,
            DELETE
        }

        public String getId() {
            return this.id;
        }

        public String getName() {
            return name;
        }

        public String getLogin() {
            return login;
        }

        public String getEmail() {
            return email;
        }

        public Calendar getCreateDate() {
            return this.createDate;
        }

        public Type getType() {
            return this.type;
        }
    }
}
