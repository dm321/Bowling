package com.europace.bowling.adapters.out;

import com.europace.bowling.dto.commands.ExitGameCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component
public class ExitGameOutboundAdapter {

    @EventListener
    public void exitGame(ExitGameCommand exitGameCommand)
    {
        System.out.println("Exiting the game");
        System.exit(0);
    }
}
