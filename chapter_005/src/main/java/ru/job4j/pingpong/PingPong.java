package ru.job4j.pingpong;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Приложение пинг-понг.
 * @author Denis Seleznev
 * @version $Id$
 * @since 22.04.2018
 */
public class PingPong extends Application {
    private static final String JOB4J = "Пинг - понг www.job4j.ru";

    /**
     * Точка запуска приложения.
     * @param stage главный контейнер.
     */
    @Override
    public void start(Stage stage) {
        int limitX = 300;
        int limitY = 300;
        Group group = new Group();
        Rectangle rect = new Rectangle(50, 100, 10, 10);
        group.getChildren().add(rect);
        Thread pinPongThread = new Thread(new RectangleMove(rect));
        pinPongThread.start();
        stage.setScene(new Scene(group, limitX, limitY));
        stage.setTitle(JOB4J);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(event -> pinPongThread.interrupt());
    }
}
