package com.europace.bowling.adapters.out;

import com.europace.bowling.dto.events.events.GameOverEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component
public class GameOverOutboundAdapter {


    @EventListener
    public void handleGameOverEvent(GameOverEvent gameOverEvent) {
        System.out.println(String.format("Exiting the game, Your score is %s", gameOverEvent.finalScore()));
        System.exit(0);
    }

}
