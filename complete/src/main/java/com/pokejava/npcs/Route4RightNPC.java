package com.pokejava.npcs;

import com.pokejava.*;
import com.pokejava.pokejavas.*;

public class Route4RightNPC extends NPC {

    // Override
    @Override
    public InteractionInfo interacted(String userAnswer, NPC trainer) { 
        if (!defeated) {
            return new InteractionInfo("I saw you and now we will battle.", null, true); 
        } else {
            return new InteractionInfo("Leave me alone.", null, false); 
        }
    }

    public Route4RightNPC(Position p) { 
        super("David", p, new PokeJava[]{ new Firely(4),}); 
        this.wantsToInteract = true;
    }
    
    // Override
    @Override
    public void setTrainerSeen(Position trainerPos) {

        if (this.p.x() == trainerPos.x()) {
            this.trainerSeen = true;
        }

    }

    // Override
    @Override
    public boolean autoAction(Map map) {

        // get target Position of trainer
        Position targetPos = map.getTrainer().getTargetPosition();

        // only one npc answer
        if (defeated && !p.equals(initPos)) { 
            // Find the way back
            findNextMove(map, this.p, initPos);
            // only wants to interact if not defeated
            this.wantsToInteract =  false;
            return false;
        }

        if (trainerSeen && wantsToInteract) {
            if (p.equals(targetPos)) { return true; }
            else { findNextMove(map, p, targetPos); }
        }
        return false;

    }

}
