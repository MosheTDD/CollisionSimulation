package me.moshe.collisionsimulation;

import javafx.animation.TranslateTransition;
import javafx.scene.control.skin.TextInputControlSkin;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Ball extends Circle{

    private int speed, velocity;
    private DIRECTION direction;
    private int pixelsToMove = 10;
    private final TranslateTransition translateTransition = new TranslateTransition();

    public Ball(double centerX, double centerY, double radius, Color color, int velocity, DIRECTION direction){
        super(centerX, centerY, radius, color);
        this.direction = direction;
        this.velocity = velocity / 2;
    }

    public void move(DIRECTION d){
        switch (d){
            case LEFT:
                translateBallAnimation(-pixelsToMove, 0, this.velocity);
                break;
            case RIGHT:
                translateBallAnimation(pixelsToMove, 0, this.velocity);
                break;
            case UP:
                translateBallAnimation(0, -pixelsToMove, this.velocity);
                break;
            case DOWN:
                translateBallAnimation(0, pixelsToMove, this.velocity);
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

    public int getVelocity() {
        return velocity;
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

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
}
