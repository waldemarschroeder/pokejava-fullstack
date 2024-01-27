package com.pokejava.npcs;

import com.pokejava.*;

public class COApoolNPC extends NPC {

    // Override
    @Override
    public InteractionInfo interacted(String userAnswer, Map map) { 
        return new InteractionInfo("Welcome to the best City: City of Azure.", null, false); 
    }

    public COApoolNPC(Position p) { 
        super(p); 
        this.wantsToInteract = true;
    }
    
    private Position trainerPosAction1 = new Position(16, 25);
    private Position trainerPosAction2 = new Position(9, 49);
    // Override
    @Override
    public void setTrainerSeen(Position trainerPos) {
        
        if (trainerPos.equals(trainerPosAction1) || trainerPos.equals(trainerPosAction2)) {
            this.trainerSeen = true;
        }
    }

    // Override
    @Override
    // true when trainer should ask interactin
    public boolean autoAction(Map map) {

        // get target Position of trainer
        Position targetPos = map.getTrainer().getTargetPosition();

        // only one npc answer
        if (!wantsToInteract && !p.equals(initPos)) { 
            // Find the way back
            findNextMove(map, this.p, initPos);
            return false; 
        }

        if (trainerSeen && wantsToInteract) {
            if(this.p.equals(targetPos)) {
                this.wantsToInteract = false;
                return true;
            } else {
                // Find the next move towards the trainer
                findNextMove(map, this.p, targetPos);
                //return true;
            }
        }
        return false;

    }

}
