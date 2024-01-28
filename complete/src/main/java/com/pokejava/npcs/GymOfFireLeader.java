package com.pokejava.npcs;

import com.pokejava.*;
import com.pokejava.pokejavas.*;

public class GymOfFireLeader extends NPC {

    // Override
    @Override
    public InteractionInfo interacted(String userAnswer, Map map) {         
        if (!defeated) {
            if (!map.getNpc(GymOfFireFirst.class).getDefeated() || !map.getNpc(GymOfFireSec.class).getDefeated()) {
                return new InteractionInfo("The other two guys will show you, if you are good enough.", null, false); 
            } else {
                return new InteractionInfo("You seem to have potential, but I won't give the fire badge easily!", null, true);
            } 
        } else {
            if (wantsToInteract) {
                //map.getTrainer().addBadge("Fire Badge");
                map.getTrainer().addBadge();
                this.wantsToInteract = false;
                return new InteractionInfo("You are a good trainer, here you get the fire badge.", null, false); 
            } else {
                return new InteractionInfo("Good luck in the next gym, if the programmer created it.", null, false); 
            }
        }

    }

    public GymOfFireLeader(Position p) { super("Conan", p, new PokeJava[]{ new Normie(5), new Waterly(6), new Firely(7)}); }

    private boolean wantsToInteract = true;

    // Override
    @Override
    public boolean autoAction(Map map) {
        // only for after battle talk
        if (defeated && wantsToInteract) {
            return true;
        } else { return false; }

    }

}
