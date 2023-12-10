package com.pokejava.npcs;

import com.pokejava.*;

public class Route1LeftNPC extends NPC {

    // Override
    @Override
    public InteractionInfo interacted(String userAnswer, PokeJava[] pokes) { return new InteractionInfo("Hi, my name is Peter.",
     null, false, this); }

    public Route1LeftNPC(Position p) { super(p); }

}
