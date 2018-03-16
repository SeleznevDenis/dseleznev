package ru.job4j.chess;


/**
 * Объект класса имитирует шахматную доску.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Board {

    private Figure[] figures = new Figure[32];
    private int figuresPosition;
    private Figure disturbingFigure;

    /**
     * Добавляет шахматную фигуру в массив figures.
     * @param figure
     */
    public void add(Figure figure) {
        figures[this.figuresPosition++] = figure;
    }
    public Figure[] getFigures() {
       return this.figures;
    }

    /**
     * Проверяет возможность движения фигуры от начальных координат до конечных,
     * если движение возможно, то возвращает массив клеток, которые фигура прошла.
     * @param source начальные координаты.
     * @param dest конечные координаты.
     * @return массив клеток которые прошла фигура.
     * @throws FigureNotFoundException в случае если
     * фигура не обнаружена в начальных координатах.
     */
    public Cell[] checkWay(Cell source, Cell dest) throws FigureNotFoundException, ImpossibleMoveException {
        Cell[] figureWay = {new Cell(-1, -1)};
        if (source != null && dest != null) {
            boolean doneFigureFound = false;
            for (Figure checkedFigure : figures) {
                if (checkedFigure != null && checkedFigure.position
                        != null && checkedFigure.position.equals(source)) {
                    figureWay = checkedFigure.way(source, dest);
                    doneFigureFound = true;
                    break;
                }
            }
            if (!doneFigureFound) {
                throw new FigureNotFoundException(String.format(
                        "%s: %s | %s %s", "В ячейке", source.getX(), source.getY(), "нет фигуры.")
                );
            }
        }
        return figureWay;
    }

    /**
     * Метод определяет, свободен ли путь way.
     * @param way массив клеток, который должна пройти фигура.
     * @return true, если путь свободен или false, если занят.
     */
    public boolean checkFigureWay(Cell[] way) {
        boolean done = true;
        for (Cell checkedWayPoint : way) {
            for (Figure checkedFigure : figures) {
                if (checkedFigure != null && checkedFigure.position != null
                        && checkedFigure.position.equals(checkedWayPoint)) {
                    done = false;
                    this.disturbingFigure = checkedFigure;
                    break;
                }
            }
        }
        return done;
    }

    /**
     * Возвращает позицию фигуры с заданными координатами;
     * @param cell заданные координаты.
     * @return
     */
    private int movingFigurePosition(Cell cell) {
        int movingFigurePosition = -1;
        for (int i = 0; i < figuresPosition; i++) {
            if (figures[i].position != null && figures[i].position.equals(cell)) {
                movingFigurePosition = i;
                break;
            }
        }
        return movingFigurePosition;
    }

    /**
     * Двигает фигуру, если это возможно из начальных
     * координат, в конечные координаты.
     * @param source начальные координаты.
     * @param dest конечные координаты.
     * @return true если возможно двигать фигуру.
     * @throws ImpossibleMoveException Невозможный ход.
     * @throws OccupiedWayException если путь занят описывает в какой ячейке и какая фигура мешает.
     * @throws FigureNotFoundException Фигура не найдена.
     */
    boolean move(Cell source, Cell dest) throws
            OccupiedWayException, FigureNotFoundException, ImpossibleMoveException {
        Cell[] way = checkWay(source, dest);
        boolean done = checkFigureWay(way);
        if (done) {
            this.figures[movingFigurePosition(source)] = this.figures[movingFigurePosition(source)].copy(dest);
        } else {
            throw new OccupiedWayException(String.format(
                 "%s: %s | %s, %s: %s", "Ячейка", this.disturbingFigure.position.getX(),
                           this.disturbingFigure.position.getY(), "занята фигурой",
                    this.disturbingFigure.getClass().getSimpleName()
                 )
            );
        }
       return done;
    }
}