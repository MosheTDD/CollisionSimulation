package me.moshe.collisionsimulation;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

public class FrameHandler extends AnimationTimer {

    private int currentFrame = 0;
    private int fps = 0;
    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0 ;
    private boolean arrayFilled = false ;

    @Override
    public void handle(long l) {
        currentFrame++;
        calcFPS(l);
    }

    public void calcFPS(long now){
        long oldFrameTime = frameTimes[frameTimeIndex] ;
        frameTimes[frameTimeIndex] = now ;
        frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length ;
        if (frameTimeIndex == 0) {
            arrayFilled = true ;
        }
        if (arrayFilled) {
            long elapsedNanos = now - oldFrameTime ;
            long elapsedNanosPerFrame = elapsedNanos / frameTimes.length ;
            double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame ;
            fps = (int) frameRate;
        }
    }

    public int getFps() {
        return fps;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    
    public void stop(){
        super.stop();
        currentFrame = 0;
    }
}
