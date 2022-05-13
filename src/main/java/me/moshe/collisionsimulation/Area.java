package me.moshe.collisionsimulation;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Optional;

public class Area extends Rectangle {

    public Area(double x, double y, double width, double height, Color color, boolean transparent) {
        super(x, y, width, height);
        super.setStroke(color);
        super.setStrokeWidth(5);
        if(transparent) super.setFill(Color.TRANSPARENT);
    }

    public int getLeftSide(){
        return (int) (this.getX() + 50);
    }

    public int getRightSide(){
        return (int) ((this.getX() + this.getWidth()) - 50);
    }

    public int getTop(){
        return (int) (this.getY() + 50);
    }

    public int getBottom(){
        return (int) ((this.getY() + this.getHeight()) - 50);
    }

}
