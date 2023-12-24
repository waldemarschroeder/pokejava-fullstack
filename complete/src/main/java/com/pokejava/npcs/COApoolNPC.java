package com.pokejava.npcs;

import com.pokejava.*;

public class COApoolNPC extends NPC {

    // Override
    @Override
    public InteractionInfo interacted(String userAnswer, NPC trainer) { 
        return new InteractionInfo("Welcome to the best City: City of Azure.", null, null); 
    }

    public COApoolNPC(Position p) { super(p); }
    
    private Position trainerPosAction1 = new Position(16, 25);
    private Position trainerPosAction2 = new Position(9, 49);
    // Override
    @Override
    public void setTrainerSeen(NPC trainer) {
        
        // get Position of trainer
        Position trainerPos = trainer.getPos();

        if (trainerContacted) {
            this.trainerSeen = false; 
        } else if (trainerPos.equals(trainerPosAction1) || trainerPos.equals(trainerPosAction2)) {
            this.trainerSeen = true;
        }
    }

    // Override
    @Override
    public boolean autoAction(Map map) {

        // get target Position of trainer
        Position targetPos = map.getTrainer().getTargetPosition();

        // only one npc answer
        if (trainerContacted && !p.equals(initPos)) { 
            // Find the way back
            findNextMove(map, this.p, initPos); 
            return false;
        }

        if (trainerSeen && !trainerContacted) {
            if(this.p.equals(targetPos)) {
                this.trainerContacted = true;
                map.setTrainerMayMove(true);
                return true;
            } else {
                // Find the next move towards the trainer
                findNextMove(map, this.p, targetPos);
            }
        }
        return false;

    }

}
