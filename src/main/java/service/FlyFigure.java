package service;

import model.Coord;
import model.Mapble;
import model.Figure;
import ui.Config;

public class FlyFigure {
    private Figure figure;
    private Coord coord;
    private boolean landed;
    private int ticks;
    Mapble map;

    public FlyFigure(Mapble map){
        this.map = map;
        figure = Figure.getRandom();
        coord = new Coord(Config.WIDTH/2-2, -1);
        landed = false;
        ticks = 2;
    }

    public Figure getFigure() {
        return figure;
    }

    public Coord getCoord() {
        return coord;
    }

    public boolean isLanded(){ //Проверка что фигура приземлилась
        return landed;
    }

    public boolean canPlaceFigure() {
        return canMoveFigure(figure,0,0);
    }

    private boolean canMoveFigure(Figure figure, int sx, int sy) {
        if (coord.x + sx + figure.top.x < 0) return false;
        if (coord.x + sx + figure.bot.x >= Config.WIDTH) return false;
        if (coord.y + sy + figure.bot.y >= Config.HEIGHT) return false;

        for (Coord dot : figure.dot)
            if (map.getBoxColor(coord.x + dot.x + sx, coord.y + dot.y + sy) > 0)
                return false;
        return true;
    }

    public void moveFigure(int sx, int sy) {
        if (canMoveFigure(figure, sx, sy))
            coord = coord.plus(sx, sy);
        else
        if (sy == 1)
            if (ticks > 0)
                ticks --;
            else
                landed = true;

    }

    public void turnFigure(int direction) {
        Figure rotated = direction == 1 ? figure.turnRight(): figure.turnLeft();
        if (!canMoveFigure(rotated, 0, 0)) return; //Проверка можно ли повернуть фигуру
        figure = rotated;
    }
}
