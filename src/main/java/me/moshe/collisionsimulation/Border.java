package me.moshe.collisionsimulation;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class Border {

    private final Line left = new Line();
    private final Line top = new Line();
    private final Line right = new Line();
    private final Line bottom = new Line();

    private final int x;
    private final int y;
    private final int width, height;
    private final int strokeWidth;

    private final Color color;

    public Border(int x, int y, int width, int height){
        this(x, y, width, height, 10, Color.BLACK);
    }

    public Border(int x, int y, int width, int height, int strokeWidth, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.strokeWidth = strokeWidth;
        this.color = color;

        constructLines();
    }

    private void constructLines(){
        Collection<Line> lines = List.of(left, top, right, bottom);
        int dividedWidth = width / 2;
        int dividedHeight = height / 2;

        int leftX = x - dividedWidth;
        int rightX = x + dividedWidth;
        left.setStartX(leftX);
        left.setEndX(leftX);
        left.setStartY(y - dividedHeight);
        left.setEndY(y + dividedHeight);

        top.setStartX(leftX);
        top.setEndX(rightX);
        top.setStartY(y - dividedHeight);
        top.setEndY(y - dividedHeight);

        right.setStartX(rightX);
        right.setEndX(rightX);
        right.setStartY(y - dividedHeight);
        right.setEndY(y + dividedHeight);

        bottom.setStartX(leftX);
        bottom.setEndX(rightX);
        bottom.setStartY(y + dividedHeight);
        bottom.setEndY(y + dividedHeight);




        lines.forEach(line -> {
            line.setStrokeWidth(strokeWidth);
            line.setStroke(color);
            line.setViewOrder(3);
        });

    }

    public Line getLeft() {
        return left;
    }

    public Line getRight() {
        return right;
    }

    public Line getTop() {
        return top;
    }

    public Line getBottom() {
        return bottom;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Collection<Line> getLines(){
        return List.of(left, top, right, bottom);
    }

}
