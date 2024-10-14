package com.europace.bowling.adapters.in;

import com.europace.bowling.dto.commands.ExitGameCommand;
import com.europace.bowling.dto.commands.RollBallCommand;
import com.europace.bowling.dto.events.events.GameStartedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component
public class ConsoleInboundAdapter {

    private final ApplicationEventPublisher publisher;
    private final Scanner scanner = new Scanner(System.in);

    @EventListener
    public void listenOnPlayerInteractions(GameStartedEvent gameStartedEvent) {
        // Keep application running by looping indefinitely, waiting for user input
        while (true) {
            Optional<String> consoleInput = findConsoleInput();
            if (consoleInput.isEmpty()) {
                try {
                    Thread.sleep(100); // Short delay to prevent tight looping
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }

            String input = consoleInput.get();
            if (input.isEmpty()) {
                publisher.publishEvent(new RollBallCommand());
            } else if (input.equalsIgnoreCase("exit")) {
                publisher.publishEvent(new ExitGameCommand());
                break;
            }
        }
    }

    private Optional<String> findConsoleInput() {
        try {
            return Optional.of(scanner.nextLine());
        } catch (NoSuchElementException e) {
            // This is where we would catch an exception if input fails in non-interactive mode
        }
        return Optional.empty();
    }
}
