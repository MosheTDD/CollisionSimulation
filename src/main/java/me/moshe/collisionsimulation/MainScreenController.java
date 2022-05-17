package me.moshe.collisionsimulation;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.*;
import java.util.function.Consumer;

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
    //private final Border border = new Border(135, 73, 900, 625, Color.rgb(142, 225, 147), true);
    private Border border = new Border(1080/2, 720/2, 900, 625);
    private final FrameHandler frameHandler = new FrameHandler();
    private int currentFrame;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startBtn.setViewOrder(1);
        stopBtn.setViewOrder(2);
        anchorPane.getChildren().addAll(border.getLines());
    }

    @FXML
    public void spawnBalls() {
        playing = false;
        ballSimulatorLabel.setVisible(false);
        clearBallArray(ball_arr);
        anchorPane.getChildren().removeIf(n -> (n instanceof Ball));
        for (int i = 0; i < ball_arr.length; i++) {
            //ball_arr[i] = new Ball(generateRandom(border.getLeftSide(), border.getRightSide()), generateRandom(border.getTop(), border.getBottom()), 20, Color.HOTPINK, VELOCITY, getRandomDirection());
            //anchorPane.getChildren().add(ball_arr[i]);
        }
    }

    @FXML
    public void start(){
        if (isEmpty(ball_arr)){
            System.out.println("Array is empty. Please generate balls.");
            return;
        }
        currentFrame = 0;
        generateBallsBtn.setVisible(false);
        startBtn.setVisible(false);
        stopBtn.setVisible(true);
        startSim();
        frameHandler.start();
    }

    private void startSim() {
        playing = true;
        int collisionFrame = 0;
        for (Ball ball:ball_arr) {
            //collisionFrame = calculateCollisionFrame(ball.getBoundsInParent().getCenterX(), ball.getRadius(), border.getLeftSide(), currentFrame);
        }
        Timer checker = new Timer();
        int finalCollisionFrame = collisionFrame;
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
                        if(finalCollisionFrame <= frameHandler.getCurrentFrame()) {
                            ball.setOppositeDirection();
                            checker.cancel();
                            checker.purge();
                        }
                    }
                });
            }
        }, 0, 100);
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

    /**
     * Stops the simulation
     * Clears all {@link Ball}s from the screen and stops the simulation if its started.
     */
    @FXML
    public void stop(){
        anchorPane.getChildren().removeIf(n -> (n instanceof Ball));
        stopBtn.setVisible(false);
        startBtn.setVisible(true);
        generateBallsBtn.setVisible(true);
        playing = false;
        frameHandler.stop();
        clearBallArray(ball_arr);
    }

    private int calculateCollisionFrame(double x, double radius, int left, double frame){
        //System.out.println("Left: " + left + "\nX: " + x + "\nFrame: " + frame);
        if(x - radius <= left)
            return (int) frame;
        frame++;
        return calculateCollisionFrame(x - 2.27, radius, left, frame);
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

    /**
     * Generate a random number within a given bound
     *
     * @param min The minimum for the bound
     * @param max The maximum for the bound
     *
     * @return The random number between the given bound
     */
    private int generateRandom(int min, int max){
        return rnd.nextInt(max - min) + min;
    }
//    private int calcBallAmount(int r){
//        return (int) ((border.getWidth() / (2*r)) * (border.getHeight() / (2*r)));
//    }
    private DIRECTION getRandomDirection(){
        return directions.get(rnd.nextInt(directions.size()));
    }


}