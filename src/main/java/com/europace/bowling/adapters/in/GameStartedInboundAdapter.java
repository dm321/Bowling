package com.europace.bowling.adapters.in;

import com.europace.bowling.BowlingService;
import com.europace.bowling.model.Game;
import com.europace.bowling.dto.events.events.GameStartedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GameStartedInboundAdapter {

    private final ApplicationEventPublisher publisher;
    private final BowlingService bowlingService;


    @EventListener
    public void startGame(ApplicationReadyEvent applicationReadyEvent) {
        System.out.println("Welcome to the Bowling Game!");
        System.out.println("Press ENTER to roll the ball. Type 'exit' to finish the game.");

        bowlingService.startGame();

    }
}
