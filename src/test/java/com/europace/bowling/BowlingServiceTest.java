package com.europace.bowling;

import com.europace.bowling.adapters.in.ConsoleInboundAdapter;
import com.europace.bowling.adapters.in.GameStartedInboundAdapter;
import com.europace.bowling.adapters.out.GameOverOutboundAdapter;
import com.europace.bowling.dto.events.events.GameOverEvent;
import com.europace.bowling.helper.RollHelper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BowlingServiceTest {

    @MockBean
    private ApplicationEventPublisher publisher;

    @MockBean
    private ConsoleInboundAdapter consoleInboundAdapter;

    @MockBean
    private GameStartedInboundAdapter gameStartedInboundAdapter;

    @MockBean
    private GameOverOutboundAdapter gameOverOutboundAdapter;

    @MockBean
    private RollHelper rollHelper;


    private final BowlingService bowlingService;


    @Test
    void testWithRealData() {

        // given
        Queue<Integer> rolls = new LinkedList<>(List.of(1, 4, 4, 5, 6, 4, 5, 5, 10, 0, 1, 7, 3, 6, 4, 10, 2, 8, 6));


        Mockito.when(rollHelper.generateRandomRoll()).thenAnswer(invocation -> rolls.poll());
        bowlingService.startGame();

        // when
        // Simulate rolling until game is over
        for (int i = 0; i < 20; i++) {
            bowlingService.rollBall();
        }


        // then
        ArgumentCaptor<GameOverEvent> gameOverCaptor = ArgumentCaptor.forClass(GameOverEvent.class);
        verify(gameOverOutboundAdapter).handleGameOverEvent(gameOverCaptor.capture());
        assertThat(gameOverCaptor.getValue().finalScore()).isEqualTo(133);

    }

    @Test
    void testTwoStrikesAtTheEnd() {

        // given
        Queue<Integer> rolls = new LinkedList<>(List.of(1, 4, 4, 5, 6, 4, 5, 5, 10, 0, 1, 7, 3, 6, 4, 10, 10, 10, 6));

        Mockito.when(rollHelper.generateRandomRoll()).thenAnswer(invocation -> rolls.poll());
        bowlingService.startGame();

        // when
        // Simulate rolling until game is over
        for (int i = 0; i < 20; i++) {
            bowlingService.rollBall();
        }

        // then
        ArgumentCaptor<GameOverEvent> gameOverCaptor = ArgumentCaptor.forClass(GameOverEvent.class);
        verify(gameOverOutboundAdapter).handleGameOverEvent(gameOverCaptor.capture());
        assertThat(gameOverCaptor.getValue().finalScore()).isEqualTo(143);

    }
}