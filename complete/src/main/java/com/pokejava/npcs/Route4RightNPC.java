package com.pokejava.npcs;

import com.pokejava.*;
import com.pokejava.pokejavas.*;

public class Route4RightNPC extends NPC {

    // Override
    @Override
    public InteractionInfo interacted(String userAnswer, NPC trainer) { return new InteractionInfo("I saw you and now we will battle.",
     null, new Battle(trainer, this)); }

    public Route4RightNPC(Position p) { super("David", p, new PokeJava[]{ new Firely(4),}); }
    
    // Override
    @Override
    public void setTrainerSeen(Map map) {
        
        // get Position of trainer
        Position trainerPos = map.getTrainer().getPos();

        if (trainerContacted) {
            this.trainerSeen = false; 
        } else if (this.p.x() == trainerPos.x()) {
            this.trainerSeen = true;
            map.setTrainerMayMove(false);
        }
    }

    // Override
    @Override
    public InteractionInfo autoAction(Map map) {

        // get Position of trainer
        Position trainerPos = map.getTrainer().getPos();

        // only one npc answer
        if (trainerContacted) { return null; }

        // if npc is defeated, let the trainer go
        // why is the line not nes. ?
        //if (defeated) { map.setTrainerMayMove(true); }

        if (trainerSeen) {
            while(move(map, "down")) {}
            if(this.getTargetPosition().equals(trainerPos)) {
                //map.setTrainerMayMove(true);
                this.trainerContacted = true;
                return interacted(null, map.getTrainer());
            }
        }
        return null;

    }

}
