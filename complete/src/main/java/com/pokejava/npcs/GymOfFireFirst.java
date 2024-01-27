package com.pokejava.npcs;

import com.pokejava.*;
import com.pokejava.pokejavas.Normie;

public class GymOfFireFirst extends NPC {

    // Override
    @Override
    public InteractionInfo interacted(String userAnswer, Map map) {         
        if (!defeated) {  
            return new InteractionInfo("So, you want to fight the gym leader? You will run back to home!", null, true); 
        } else {
            return new InteractionInfo("I should find a new job. Any suggestions?", null, false); 
        }

    }

    public GymOfFireFirst(Position p) { super("Ron", p, new PokeJava[]{ new Normie(4),}); }

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
