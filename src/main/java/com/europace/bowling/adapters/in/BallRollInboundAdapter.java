package com.europace.bowling.adapters.in;

import com.europace.bowling.BowlingService;
import com.europace.bowling.dto.commands.RollBallCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component
public class BallRollInboundAdapter {

    private final ApplicationEventPublisher publisher;

    private final BowlingService bowlingService;

    @EventListener
    public void listenOnRolls(RollBallCommand rollBallCommand) {
        System.out.println("Rolling...");
        bowlingService.rollBall();
    }
}
