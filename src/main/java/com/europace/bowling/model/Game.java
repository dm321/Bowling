package com.europace.bowling.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Game {
    private final List<Frame> frames = new ArrayList<>();
    private int currentFrameIndex = 0;
    private boolean finished = false;

    public Game() {
        for (int i = 0; i < 10; i++) {
            frames.add(new Frame());
        }
    }

    public void advanceFrame() {
        if (frames.get(currentFrameIndex).isComplete()) {
            currentFrameIndex++;
        }
    }

    public void finishGame() {
        finished = true;
    }

    public boolean isFinished() {
        return finished;
    }

    public int getCurrentFrameIndex() {
        return currentFrameIndex;
    }

    public Frame getCurrentFrame()
    {
        return this.getFrames().get(currentFrameIndex);
    }
}