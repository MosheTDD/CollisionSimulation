package me.moshe.collisionsimulation;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Balls {

    private final Random rnd = MainScreenController.rnd;
    private final int amount;
    private int speed;
    private final Color color;
    private final int radius;
    private final List<Ball> balls = new ArrayList<>();
    private final List<DIRECTION> directions = List.of(DIRECTION.values());
    private final Border border = MainScreenController.getBorder();

    public Balls(int amount, int speed){
        this(amount, speed, Color.BLACK, 20);
    }

    public Balls(int amount, int speed, Color color, int radius){
        this.amount = amount;
        this.speed = speed;
        this.color = color;
        this.radius = radius;
    }

    public void addBalls(){
        for (int i = 0; i < this.amount; i++)
            balls.add(new Ball(generateRandom((int)border.getLeft().getStartX() + 40, (int) border.getRight().getStartX() - 40),
                    generateRandom((int) border.getTop().getStartY() + 40, (int) border.getBottom().getStartY() - 40), radius, color, speed, DIRECTION.LEFT));
    }

    private DIRECTION getRandomDirection(){
        return directions.get(rnd.nextInt(directions.size()));
    }
    private int generateRandom(int min, int max){
        return rnd.nextInt(max - min) + min;
    }

    public Random getRnd() {
        return rnd;
    }

    public int getAmount() {
        return amount;
    }

    public int getSpeed() {
        return speed;
    }

    public Color getColor() {
        return color;
    }

    public int getRadius() {
        return radius;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public List<DIRECTION> getDirections() {
        return directions;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
