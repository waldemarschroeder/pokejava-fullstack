package com.pokejava.npcs;

import com.pokejava.*;
import com.pokejava.pokejavas.*;

public class Route4RightNPC extends NPC {

    // Override
    @Override
    public InteractionInfo interacted(String userAnswer, NPC trainer) { 
        if (!defeated) {
            return new InteractionInfo("I saw you and now we will battle.", null, new Battle(trainer, this)); 
        } else {
            return new InteractionInfo("Leave me alone.", null, null); 
        }
    }

    public Route4RightNPC(Position p) { super("David", p, new PokeJava[]{ new Firely(4),}); }
    
    // Override
    @Override
    public void setTrainerSeen(NPC trainer) {
        
        // get Position of trainer
        Position trainerPos = trainer.getPos();

        if (trainerContacted) {
            this.trainerSeen = false; 
        } else if (this.p.x() == trainerPos.x()) {
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
            return false;
        }

        // if npc is defeated, let the trainer go
        // why is the line not nes. ?
        //if (defeated) { map.setTrainerMayMove(true); }

        if (trainerSeen && !trainerContacted) {
            if(p.equals(targetPos)) {
                this.trainerContacted = true;
                return true;
            }
            findNextMove(map, p, targetPos);
        }
        return false;

    }

}
