package ru.job4j.condition;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class DummyBot {
    /**
     * answer
     * Отвечает на вопросы.
     * @param question Вопрос от клиента.
     * @return Ответ.
     */
   public String answer(String question) {
       String answer = "Это ставит меня в тупик. Спросите другой вопрос.";
       if ("Привет, Бот.".equals(question)) {
           answer = "Привет, умник.";
       } else if ("Пока.".equals(question)) {
           answer = "До скорой встречи.";
       }
       return answer;
   }
}
