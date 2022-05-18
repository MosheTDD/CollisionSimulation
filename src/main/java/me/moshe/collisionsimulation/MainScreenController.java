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
    @FXML public Label ballSimulatorLabel, fpsLabel;
    @FXML public Button generateBallsBtn,startBtn, stopBtn;

    private final int WIN_WIDTH = Main.getWinWidth();
    private final int WIN_HEIGHT = Main.getWinHeight();

    private boolean playing = false;
    private int currentFrame;

    private final FrameHandler frameHandler = new FrameHandler();
    private final Balls balls = new Balls(1, 50, Color.HOTPINK, 20);

    public final static Random rnd = new Random();
    public final static Border border = new Border(1080/2, 720/2, 800, 585, 10, Color.LIGHTGREEN);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startBtn.setViewOrder(1);
        stopBtn.setViewOrder(2);
        anchorPane.getChildren().addAll(border.getLines());
    }

    @FXML
    public void spawnBalls() {
        balls.getBalls().clear();
        balls.addBalls();
        playing = false;
        ballSimulatorLabel.setVisible(false);
        anchorPane.getChildren().removeIf(n -> (n instanceof Ball));
        anchorPane.getChildren().addAll(balls.getBalls());
    }

    @FXML
    public void start(){
        if (balls.getBalls().isEmpty()){
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
                    for (Ball ball: balls.getBalls()) {
                        ball.checkIntersection(border.getLeft(), border.getTop(), border.getRight(), border.getBottom());
                    }
                    fpsLabel.setText(String.valueOf(frameHandler.getFps()));
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
                    for (Ball b : balls.getBalls()){
                        b.move(DIRECTION.LEFT);
                    }
                });
            }
        }, 0, 10);
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
        balls.getBalls().clear();
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

    public static Border getBorder(){
        return border;
    }

    public static Random getRnd(){
        return rnd;
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


}