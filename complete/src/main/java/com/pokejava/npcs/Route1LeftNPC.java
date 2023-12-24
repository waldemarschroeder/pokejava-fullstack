package com.pokejava.npcs;

import com.pokejava.*;

public class Route1LeftNPC extends NPC {

    // Override
    @Override
    public InteractionInfo interacted(String userAnswer, NPC trainer) { return new InteractionInfo("Hi, my name is Peter.",
     null, false); }

    public Route1LeftNPC(Position p) { super(p); }

}
