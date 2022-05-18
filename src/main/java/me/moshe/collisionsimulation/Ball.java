package me.moshe.collisionsimulation;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.skin.TextInputControlSkin;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class Ball extends Circle{

    private int speed;
    private DIRECTION direction;
    private int pixelsToMove = 10;
    private final TranslateTransition translateTransition = new TranslateTransition();

    public Ball(double centerX, double centerY, double radius, Color color, int speed, DIRECTION direction){
        super(centerX, centerY, radius, color);
        this.direction = direction;
        this.speed = speed;
        this.setViewOrder(3);
    }

    public void checkIntersection(Node left, Node top, Node right, Node bottom) {
        Bounds ballBounds = this.localToScene(this.getBoundsInLocal());
        Bounds leftBounds = left.localToScene(left.getBoundsInParent());
        Bounds topBounds = top.localToScene(top.getBoundsInParent());
        Bounds rightBounds = right.localToScene(right.getBoundsInParent());
        Bounds bottomBounds = bottom.localToScene(bottom.getBoundsInParent());
        if (leftBounds.intersects(ballBounds) || topBounds.intersects(ballBounds) ||
                rightBounds.intersects(ballBounds) || bottomBounds.intersects(ballBounds)) {
            System.out.println("hit");
            setOppositeDirection();
        }
    }

    public void move(DIRECTION d){
        switch (d){
            case LEFT:
                translateBallAnimation(-pixelsToMove, 0, this.speed);
                break;
            case RIGHT:
                translateBallAnimation(pixelsToMove, 0, this.speed);
                break;
            case UP:
                translateBallAnimation(0, -pixelsToMove, this.speed);
                break;
            case DOWN:
                translateBallAnimation(0, pixelsToMove, this.speed);
                break;
        }
    }

    public void setOppositeDirection(){
        this.setPixelsToMove(this.getPixelsToMove() * (-1));
    }

    private void translateBallAnimation(double x, double y, int velocity) {
        this.translateTransition.setNode(this);
        this.translateTransition.setByX(x);
        this.translateTransition.setByY(y);
        this.translateTransition.setDuration(Duration.millis(velocity));
        this.translateTransition.play();
    }

    public DIRECTION getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public int getPixelsToMove() {
        return pixelsToMove;
    }

    public void setPixelsToMove(int pixelsToMove) {
        this.pixelsToMove = pixelsToMove;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDirection(DIRECTION direction) {
        this.direction = direction;
    }
}
