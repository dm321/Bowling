package com.europace.bowling.dto.events.events;

import com.europace.bowling.model.Game;

public record BallRolledEvent(int pinsRolled, Game game, int currentScore) {
}
