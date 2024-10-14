package com.europace.bowling.adapters.out;

import com.europace.bowling.model.Frame;
import com.europace.bowling.model.Game;
import com.europace.bowling.dto.events.events.BallRolledEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ScoreOutboundAdapter{


    @EventListener
    public void displayScore(BallRolledEvent ballRolledEvent) {
        System.out.println("Rolled: " + ballRolledEvent.pinsRolled() + " pins.");

        Game game = ballRolledEvent.game();
        Frame currentFrame = game.getFrames().get(game.getCurrentFrameIndex());
        int rollsLeft = currentFrame.isComplete() ? 0 : 1;
        System.out.println("Current Score: " + ballRolledEvent.currentScore());
    }

}
