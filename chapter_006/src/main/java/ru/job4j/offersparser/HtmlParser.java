package ru.job4j.offersparser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Парсер вакансий.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 0.1
 */
public class HtmlParser {
    /**
     * LOG4j логгер.
     */
    private static final Logger LOG = LogManager.getLogger("forParser");

    /**
     * Регулярное выражения для поиска времени вакансии.
     */
    private static final String REGEX_TIME = "[0-2][0-9]:[0-6][0-9]";

    /**
     * Регулярное выражение для поиска даты вакансии в числах.
     */
    private static final String REGEX_DATE = "[0-3]?\\d\\s[а-я]{3}\\s\\d\\d,\\s";

    /**
     * Регулярное выражение для поиска сегодняшней даты.
     */
    private static final String REGEX_TODAY = "сегодня,\\s";

    /**
     * Регулярное выражение для поиска вчерашней даты.
     */
    private static final String REGEX_YESTERDAY = "вчера,\\s";

    /**
     * User Agent использующийся при подключении парсера к сайту
     */
    private String userAgent;

    /**
     * Искомая строка в описании вакансии.
     */
    private String searchString;

    /**
     * Исключающая строка в описании вакансии.
     */
    private String excludedString;

    /**
     * Хранилище найденых заявок.
     */
    private final Set<Vacancy> foundVacancies = new HashSet<>();

    /**
     * Флаг актуальности даты вакансии.
     */
    private boolean actualDate = true;

    /**
     * Дата остановки поиска.
     */
    private Calendar stopDate;

    private String url;

    public void init() throws IOException {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("parser/parserSettings.properties")) {
            Properties settings = new Properties();
            settings.load(stream);
            this.searchString = settings.getProperty("searchString");
            this.excludedString = settings.getProperty("excludedString");
            int year = Integer.parseInt(settings.getProperty("year"));
            int month = Integer.parseInt(settings.getProperty("month"));
            int day = Integer.parseInt(settings.getProperty("day"));
            this.stopDate = new GregorianCalendar(year, month, day);
            this.userAgent = settings.getProperty("userAgent");
            this.url = settings.getProperty("url");
        }
    }

    /**
     * Производит сбор данных со страниц сайта. В каждом цикле прибавляет следующее число
     * к адресу, начинает с единицы.
     * @return HashSet содержащий найденые вакансии.
     * @throws IOException выбразывает при невозможности подкючиться к сайту.
     */
    public Set<Vacancy> parseUrl() throws IOException {
        foundVacancies.clear();
        for (int i = 1; this.actualDate; i++) {
            Document doc = Jsoup.connect(this.url + i).userAgent(this.userAgent).get();
            getVacancyFromPage(doc);
            LOG.info(String.format("%s: %s. Was checked", "Page", i));
        }
        return this.foundVacancies;
    }

    /**
     * Производит сбор данных из HTML файла.
     * @param file файл для сбора данных.
     * @return HashSet содержащий найденные вакансии.
     * @throws IOException выбрасывает при невозможности подключиться к файлу.
     */
    public Set<Vacancy> parseFile(File file) throws IOException {
        this.foundVacancies.clear();
        Document doc = Jsoup.parse(file, "UTF-8");
        this.getVacancyFromPage(doc);
        return this.foundVacancies;
    }

    /**
     * Производит сбор вакансий из объекта Document.
     * @param doc объект для сбора вакансий.
     */
    private void getVacancyFromPage(Document doc) {
        Elements trElements = doc.getElementsByTag("tr");
        for (Element trCurrent : trElements) {
            Vacancy newVacancy = new Vacancy();
            boolean vacancyFounded = false;
            for (Element child : trCurrent.children()) {
                String searchString = child.text().toLowerCase();
                if (searchString.contains(this.searchString)
                        && !searchString.contains(this.excludedString)) {
                    newVacancy.setText(child.text());
                    vacancyFounded = true;
                    for (Element ch : child.children()) {
                        if (!ch.attr("href").contains("actualutils")) {
                            newVacancy.setUrl(ch.attr("href"));
                        }
                    }
                } else if (searchString.matches(REGEX_DATE + REGEX_TIME)
                        || searchString.matches(REGEX_TODAY + REGEX_TIME)
                        || searchString.matches(REGEX_YESTERDAY + REGEX_TIME)) {
                    Calendar vacancyDate = getDataFromString(searchString);
                    if (vacancyFounded && !this.checkDate(vacancyDate)) {
                        this.actualDate = false;
                        LOG.info(String.format("Search completed, was found: %s vacancies", this.foundVacancies.size()));
                    }
                    newVacancy.setDate(vacancyDate);
                }
            }
            if (!this.actualDate) {
                break;
            }
            if (newVacancy.isFull()) {
                this.foundVacancies.add(newVacancy);
            }
        }
    }

    /**
     * Проверяет дату, на актуальность.
     * @param vacancyDate дата для проверки.
     * @return true если дата актуальна, в противном случае false.
     */
    private boolean checkDate(Calendar vacancyDate) {
        boolean done = false;
        if (vacancyDate.after(this.stopDate)) {
            done = true;
        }
        return done;
    }

    /**
     * Преобразует дату из строки в Calendar.
     * @param dateForParse строка с датой.
     * @return Calendar.
     */
    private Calendar getDataFromString(String dateForParse) {
        DateFormat dfForCurrentDate = new SimpleDateFormat("d MMM y", new Locale("ru"));
        String currentFormattedDate = dfForCurrentDate.format(new Date());
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1);
        String yesterdayFormattedDate = dfForCurrentDate.format(cal.getTime());
        String replacedDate = dateForParse
                .replace("вчера", yesterdayFormattedDate)
                .replace("сегодня", currentFormattedDate);
        DateFormat df = new SimpleDateFormat("d MMM y, HH:mm", new Locale("ru"));
        Date date = null;
        try {
            date = df.parse(replacedDate);
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
        }
        Calendar calendar = new GregorianCalendar();
        if (date != null) {
            calendar.setTime(date);
        }
        return calendar;
    }

    /**
     * Устанавливает дату для остановки парсинга.
     * @param stopDate дата остановки.
     */
    public void setStopDate(Calendar stopDate) {
        this.stopDate = stopDate;
    }
}