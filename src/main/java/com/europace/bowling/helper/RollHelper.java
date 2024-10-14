package com.europace.bowling.helper;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RollHelper {

    public int generateRandomRoll() {
        return new Random().nextInt(11); // Randomly roll between 0 and 10
    }
}
