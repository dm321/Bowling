package com.europace.bowling.dto.events.events;

import com.europace.bowling.model.Game;

public record GameStartedEvent(Game game) {
}
