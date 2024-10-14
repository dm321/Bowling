package com.europace.bowling;

import com.europace.bowling.dto.events.events.BallRolledEvent;
import com.europace.bowling.dto.events.events.GameOverEvent;
import com.europace.bowling.dto.events.events.GameStartedEvent;
import com.europace.bowling.helper.RollHelper;
import com.europace.bowling.model.Frame;
import com.europace.bowling.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BowlingService {
    private final ApplicationEventPublisher publisher;
    private Game game;
    private final RollHelper rollHelper;

    public void startGame() {
        game = new Game();
        publisher.publishEvent(new GameStartedEvent(game));
    }

    public void rollBall() {
        if (game.isFinished()) {
            return; // Don't roll after the game is finished.
        }

        int pins = rollHelper.generateRandomRoll();

        if (game.getCurrentFrameIndex() < 9) {
            int pinsLeft = 10 - game.getCurrentFrame().getFirstRoll();
            pins = pins > pinsLeft ? pinsLeft : pins;
        }

        processRoll(pins, game);
    }

    private void processRoll(int pins, Game game) {
        Frame currentFrame = game.getCurrentFrame();
        currentFrame.roll(pins);

        // Handle 10th frame extra roll logic.
        if (game.getCurrentFrameIndex() == 9) {
            handleTenthFrame(currentFrame, game);
        } else {
            if (currentFrame.isComplete()) {
                game.advanceFrame();
            }
        }

        publisher.publishEvent(new BallRolledEvent(pins, game, calculateScore(game)));

        if (game.isFinished()) {
            publisher.publishEvent(new GameOverEvent(calculateScore(game)));
        }
    }

    private void handleTenthFrame(Frame currentFrame, Game game) {
        // Allow a third roll if it's a strike or spare
        if ((currentFrame.isStrike() || currentFrame.isSpare()) && !currentFrame.hasRolledThirdBall()) {
            currentFrame.setCanRollThirdBall(true); // Allow extra roll in 10th frame.
        }

        // Game ends if the third roll is completed (if allowed) or the second roll (in case of no strike or spare)
        if (currentFrame.isExtraRollComplete() || (!currentFrame.isStrike() && !currentFrame.isSpare() && currentFrame.isComplete())) {
            game.finishGame();
        }
    }

    private int calculateScore(Game game) {
        int totalScore = 0;
        List<Frame> frames = game.getFrames();

        for (int i = 0; i < frames.size(); i++) {
            Frame frame = frames.get(i);
            totalScore += frame.getScore();

            // Add bonuses for strikes and spares
            if (frame.isStrike() && i < 9) {
                totalScore += calculateStrikeBonus(frames, i);
            }

            if (frame.isSpare() && i < 9) {
                totalScore += frames.get(i + 1).getFirstRoll(); // Bonus from the next roll for spare
            }
        }

        return totalScore;
    }

    private int calculateStrikeBonus(List<Frame> frames, int frameIndex) {
        if (frameIndex + 1 >= frames.size()) return 0;

        Frame nextFrame = frames.get(frameIndex + 1);
        if (nextFrame.isStrike()) {
            // Get the next two rolls if the next frame is a strike
            int bonus = nextFrame.getFirstRoll();
            if (frameIndex + 2 < frames.size()) {
                bonus += frames.get(frameIndex + 2).getFirstRoll(); // Add the first roll from the frame after next
            }
            return bonus;
        } else {
            // Otherwise, sum the first two rolls of the next frame
            return nextFrame.getFirstRoll() + nextFrame.getSecondRoll();
        }
    }

    public Game getGame() {
        return game;
    }
}
