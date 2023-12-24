package com.pokejava.npcs;

import com.pokejava.*;

public class Route3TopNpc extends NPC {

    // Override
    @Override
    public InteractionInfo interacted(String userAnswer, NPC trainer) { return new InteractionInfo("I am John.",
     null, false); }

    public Route3TopNpc(Position p) { super(p); }

    private int counter = 0;
    // Override
    @Override
    public boolean autoAction(Map map) {

        int maxCount = 16;

        // Move down 8 times
        if (counter < maxCount / 2) {
            if (move(map, "down").success()) { this.counter++; }
        }
        // Move up back
        else if (counter >= maxCount / 2 && counter < maxCount) {
            if (move(map, "up").success()) { this.counter++; }
        }

        // Reset the counter
        if (counter == maxCount) {
            this.counter = 0;
        }

        return false;

    }

}
