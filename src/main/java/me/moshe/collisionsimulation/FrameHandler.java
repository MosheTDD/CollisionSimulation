package me.moshe.collisionsimulation;

import javafx.animation.AnimationTimer;

public class FrameHandler extends AnimationTimer {

    private int currentFrame = 0;

    @Override
    public void handle(long l) {
        currentFrame++;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    
    public void stop(){
        super.stop();
        currentFrame = 0;
    }
}
