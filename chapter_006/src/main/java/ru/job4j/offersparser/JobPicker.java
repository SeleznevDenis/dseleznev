package ru.job4j.offersparser;

import org.apache.logging.log4j.*;
import java.io.IOException;
import java.util.*;

/**
 * Запускает парсер вакансий по таймеру.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 0.1
 */
public class JobPicker {
    /**
     * LOG4J логгер
     */
    private static final Logger LOG = LogManager.getLogger("forParser");

    /**
     * URL адрес страницы для парсинга.
     */
    private String urlForParse = "http://www.sql.ru/forum/job-offers/";

    /**
     * Таймер, для запуска парсинга.
     */
    private final Timer timer = new Timer();

    /**
     * Задержка перед первым запуском.
     */
    private long delay = 0;

    /**
     * Периодичность запуска.
     */
    private long period = 24 * 60 * 60 * 1000;

    /**
     * Инициализирует:
     * @param delay задержка перед первым запуском.
     * @param period периодичность запуска.
     */
    public JobPicker(int delay, int period) {
        this.delay = delay;
        this.period = period;
    }

    public JobPicker() {
    }

    /**
     * Запускает таймер учитывая задержку и периодичнось запуска и устанавливает ему задачу.
     */
    public void start() {
       this.timer.schedule(new ParseTask(), this.delay, this.period);
    }

    /**
     * Останавливает таймер.
     */
    public void stop() {
        this.timer.cancel();
    }

    /**
     * Задача для таймера.
     * @author Denis Seleznev.
     * @version $Id$
     * @since 0.1
     */
    class ParseTask extends TimerTask {
        /**
         * Собирает вакансии с сайта, при первом запуске использует заданную дату для остановки
         * в последующие запуски для остановки используется дата последней найденой вакансии.
         */
        @Override
        public void run() {
            HtmlParser parser = new HtmlParser();
            try (SQLStore store = new SQLStore()) {
                parser.init();
                store.init();
                Calendar stopDate = store.getLastDate();
                if (stopDate != null) {
                    parser.setStopDate(stopDate);
                }
                store.insertVacancies(parser.parseUrl());
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }
}
