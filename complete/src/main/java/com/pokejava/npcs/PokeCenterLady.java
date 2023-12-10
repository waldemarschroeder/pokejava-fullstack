package com.pokejava.npcs;

import com.pokejava.*;

public class PokeCenterLady extends NPC {

    // Override
    @Override
    public InteractionInfo interacted(String userAnswer, PokeJava[] pokes) { 
        String npcAnswer = "";
        String[] possibleUserAnswers = null;
        
        switch(userAnswer) {
            // second answer
            case "yes": 
                npcAnswer = "Ok, your PokeJavas are now healed."; 
                for(PokeJava poke : pokes) {
                    poke.recHp(); 
                }
                break;

            // second answer
            case "no": npcAnswer = "Ok, then not"; break;
            
            // first answer
            default: 
                npcAnswer = "Should I heal your PokeJavas?"; 
                possibleUserAnswers = new String[] {"yes", "no"};
                break;
        }

        return new InteractionInfo(npcAnswer, possibleUserAnswers, false, this);

    }

    public PokeCenterLady(Position p) { super(p); }

}