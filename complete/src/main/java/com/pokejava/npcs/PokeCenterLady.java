package com.pokejava.npcs;

import com.pokejava.*;

public class PokeCenterLady extends NPC {

    // Override
    @Override
    public InteractionInfo interacted(String userAnswer, NPC trainer) { 
        String npcAnswer = "";
        String[] possibleUserAnswers = null;
        
        switch(userAnswer) {
            // second answer
            case "yes": 
                npcAnswer = "Ok, your PokeJavas are now healed."; 
                trainer.healPokes();
                break;

            // second answer
            case "no": npcAnswer = "Ok, then not"; break;
            
            // first answer
            default: 
                npcAnswer = "Should I heal your PokeJavas?"; 
                possibleUserAnswers = new String[] {"yes", "no"};
                break;
        }

        return new InteractionInfo(npcAnswer, possibleUserAnswers, false);

    }

    public PokeCenterLady(Position p) { super(p); }

}