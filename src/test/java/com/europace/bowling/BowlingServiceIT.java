package com.europace.bowling;

import com.europace.bowling.adapters.in.ConsoleInboundAdapter;
import com.europace.bowling.adapters.in.GameStartedInboundAdapter;
import com.europace.bowling.adapters.out.GameOverOutboundAdapter;
import com.europace.bowling.adapters.out.ScoreOutboundAdapter;
import com.europace.bowling.dto.events.events.BallRolledEvent;
import com.europace.bowling.dto.events.events.GameOverEvent;
import com.europace.bowling.dto.events.events.GameStartedEvent;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BowlingServiceIT {

    @MockBean
    private ScoreOutboundAdapter scoreOutboundAdapter;

    @MockBean
    private ConsoleInboundAdapter consoleInboundAdapter;

    @MockBean
    private GameStartedInboundAdapter gameStartedInboundAdapter;

    @MockBean
    private GameOverOutboundAdapter gameOverOutboundAdapter;


    private final BowlingService bowlingService;


    @Test
    void testStartGame() {
        bowlingService.startGame();
        ArgumentCaptor<GameStartedEvent> eventCaptor = ArgumentCaptor.forClass(GameStartedEvent.class);
        verify(consoleInboundAdapter).listenOnPlayerInteractions(eventCaptor.capture());
        assertThat(bowlingService.getGame()).isEqualTo(eventCaptor.getValue().game());
    }

    @Test
    void testRollBall() {
        bowlingService.startGame();
        bowlingService.rollBall();

        ArgumentCaptor<BallRolledEvent> eventCaptor = ArgumentCaptor.forClass(BallRolledEvent.class);
        verify(scoreOutboundAdapter).displayScore(eventCaptor.capture());

        // Assert that the rolled pins are within the expected range (0-10)
        assertThat(eventCaptor.getValue().pinsRolled()).isLessThan(11);
    }

    @Test
    void testGameOverEvent() {
        bowlingService.startGame();

        // Simulate rolling until game is over
        for (int i = 0; i < 30; i++) {
            bowlingService.rollBall();
        }

        ArgumentCaptor<GameOverEvent> gameOverCaptor = ArgumentCaptor.forClass(GameOverEvent.class);
        verify(gameOverOutboundAdapter).handleGameOverEvent(gameOverCaptor.capture());

    }

}
