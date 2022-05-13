package me.moshe.collisionsimulation;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainScreenController implements Initializable {
    @FXML public AnchorPane anchorPane;
    @FXML public Label ballSimulatorLabel;
    @FXML public Button generateBallsBtn,startBtn, stopBtn;
    Random rnd = new Random();
    private final int BALL_AMOUNT = 1;
    private final int WIN_WIDTH = Main.getWinWidth(), WIN_HEIGHT = Main.getWinHeight();
    private Ball[] ball_arr = new Ball[BALL_AMOUNT];
    private final List<DIRECTION> directions = List.of(DIRECTION.values());
    private boolean playing = false;
    private final int VELOCITY = 100;
    private final Area area = new Area(135, 73, 900, 625, Color.rgb(142, 225, 147), true);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startBtn.setViewOrder(1);
        stopBtn.setViewOrder(2);
        area.setViewOrder(0);
        anchorPane.getChildren().add(area);
        System.out.println(area.getLeftSide());
    }

    @FXML
    public void spawnBalls(){
        playing = false;
        ballSimulatorLabel.setVisible(false);
        clearBallArray(ball_arr);
        anchorPane.getChildren().removeIf(n -> (n instanceof Ball));
        for (int i = 0; i < ball_arr.length; i++) {
            ball_arr[i] = new Ball(generateRandom(area.getLeftSide(), area.getRightSide()), generateRandom(area.getTop(), area.getBottom()), 20, Color.HOTPINK, VELOCITY, getRandomDirection());
            anchorPane.getChildren().add(ball_arr[i]);
        }
    }

    @FXML
    public void start(){
        if (isEmpty(ball_arr)){
            System.out.println("Array is empty. Please generate balls.");
            return;
        }
        generateBallsBtn.setVisible(false);
        startBtn.setVisible(false);
        stopBtn.setVisible(true);
        startSim();
    }

    private void startSim() {
        playing = true;
        Timer checker = new Timer();
        checker.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(!playing){
                        checker.cancel();
                        checker.purge();
                        return;
                    }
                    for (Ball ball:ball_arr) {
                        ball.checkEdgeCollision(area.getLeftSide(), area.getTop(), area.getRightSide(), area.getBottom());
                    }
                });
            }
        }, 0, 1);


        Timer move = new Timer();
        move.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(!playing){
                        move.cancel();
                        move.purge();
                        return;
                    }
                    for (Ball b : ball_arr){
                        b.move(DIRECTION.LEFT);
                    }

                });
            }
        }, 0, 1);
    }

    @FXML
    public void stop(){
        anchorPane.getChildren().removeIf(n -> (n instanceof Ball));
        stopBtn.setVisible(false);
        startBtn.setVisible(true);
        generateBallsBtn.setVisible(true);
        playing = false;
        clearBallArray(ball_arr);
    }
    private void checkEdges(Ball ball) {
        if (ball.getBoundsInParent().getCenterX() - ball.getRadius() <= area.getLeftSide() || ball.getBoundsInParent().getCenterX() + ball.getRadius() >= area.getRightSide() ||
            ball.getBoundsInParent().getCenterY() - ball.getRadius() <= area.getTop() || ball.getBoundsInParent().getCenterY() + ball.getRadius() >= area.getBottom()){
                ball.setOppositeDirection();
        }
    }
    private void checkCollision(Ball b) {
        for (Ball n : ball_arr)
            if (b.getCenterX() - b.getRadius() <= n.getRadius() + n.getCenterX() || b.getRadius() + b.getCenterY() >= n.getRadius() + n.getCenterY())
                b.setOppositeDirection();
    }

    private void clearBallArray(Ball[] a){
        for (Ball b:a) {
            b = null;
        }
    }
    private boolean isEmpty(Ball[] a){
        for (Ball b: a) {
            if(b == null)
                return true;
        }
        return false;
    }
    private int generateRandom(int min, int max){
        return rnd.nextInt(max - min) + min;
    }
    private int calcBallAmount(int r){
        return (int) ((area.getWidth() / (2*r)) * (area.getHeight() / (2*r)));
    }
    private DIRECTION getRandomDirection(){
        return directions.get(rnd.nextInt(directions.size()));
    }
//    private void runTask(T[] arr, Callable<T> task) throws Exception {
//        if(arr instanceof Ball[]){
//            Ball[] bArr = (Ball[]) arr;
//            for (Ball b:bArr) {
//                task.call();
//            }
//        }
//    }


}