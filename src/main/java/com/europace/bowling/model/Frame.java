package com.europace.bowling.model;

import lombok.Getter;

import java.util.stream.IntStream;

public class Frame {
    private int firstRoll = -1;
    private int secondRoll = -1;
    private int extraRoll = -1; // For 10th frame bonus
    private boolean canRollThirdBall = false;

    public void roll(int pins) {
        if (firstRoll == -1) {
            firstRoll = pins;
        } else if (secondRoll == -1) {
            secondRoll = pins;
        } else if (canRollThirdBall && extraRoll == -1) {
            extraRoll = pins;
        }
    }

    public boolean isComplete() {
        return firstRoll == 10 || secondRoll != -1;
    }

    public boolean isExtraRollComplete() {
        return canRollThirdBall && extraRoll != -1;
    }

    public int getScore() {
        return (firstRoll == -1 ? 0 : firstRoll) + (secondRoll == -1 ? 0 : secondRoll) + (extraRoll == -1 ? 0 : extraRoll);
    }

    public boolean isSpare() {
        return firstRoll + secondRoll == 10 && firstRoll != 10;
    }

    public boolean isStrike() {
        return firstRoll == 10;
    }

    public IntStream getRolls() {
        return IntStream.of(firstRoll, secondRoll, extraRoll).filter(roll -> roll != -1);
    }

    public int getFirstRoll() {
        return this.firstRoll == -1 ? 0 : this.firstRoll;
    }

    public int getSecondRoll() {
        return this.secondRoll == -1 ? 0 : this.secondRoll;
    }

    public boolean hasRolledThirdBall() {
        return this.extraRoll != -1;
    }

    public void setCanRollThirdBall(boolean canRollThirdBall) {
        this.canRollThirdBall = canRollThirdBall;
    }
}