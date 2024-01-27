package com.pokejava.npcs;

import com.pokejava.*;
import com.pokejava.pokejavas.*;

public class GymOfFireSec extends NPC {

    // Override
    @Override
    public InteractionInfo interacted(String userAnswer, Map map) {         
        if (!defeated) {
            if (!map.getNpc(GymOfFireFirst.class).getDefeated()) {
                return new InteractionInfo("You must defeat the first guy before facing me.", null, false); 
            } else {
                return new InteractionInfo("The first guy was a looser, I'll show you the fire!", null, true);
            } 
        } else {
            return new InteractionInfo("I was defeated by a kid?! Maybe my ex wife was right.", null, false); 
        }

    }

    public GymOfFireSec(Position p) { super("Anton", p, new PokeJava[]{ new Firely(4), new Waterly(5)}); }

    private boolean wantsToInteract = true;

    // Override
    @Override
    public boolean autoAction(Map map) {
        // only for after battle talk
        if (defeated && wantsToInteract) {
            this.wantsToInteract = false;
            return true;
        } else { return false; }

    }

}
