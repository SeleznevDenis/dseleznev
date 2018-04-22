package ru.job4j.pingpong;

import javafx.scene.shape.Rectangle;

/**
 * Движение квадрата.
 * @author Denis Seleznev
 * @version $Id$
 * @since 22.04.2018
 */
public class RectangleMove implements Runnable {

    private final Rectangle rect;
    private static final double CHANGE_DIRECTION = -1;
    private double moveX = 1;
    private double moveY = 0.3D;

    /**
     * Инициализирует квадрат.
     * @param rect квадрат.
     */
    public RectangleMove(Rectangle rect) {
        this.rect = rect;
    }

    /**
     * Меняет направление по оси Х.
     */
    private void changeDirectionX() {
        this.moveX *= CHANGE_DIRECTION;
    }

    /**
     * Меняет направление по оси Y.
     */
    private void changeDirectionY() {
        this.moveY *= CHANGE_DIRECTION;
    }

    /**
     * Каждые 60 миллисекунд меняет координаты квадрата.
     * Если достигнута граница меняет вектор движения по оси.
     */
    @Override
    public void run() {
        while (true) {
            double rectX = this.rect.getX();
            double rectY = this.rect.getY();
            if (rectX >= 300D || rectX <= 0D) {
                changeDirectionX();
            }
            if (rectY >= 300D || rectY <= 0D) {
                changeDirectionY();
            }
            this.rect.setX(rectX + this.moveX);
            this.rect.setY(rectY + this.moveY);
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
