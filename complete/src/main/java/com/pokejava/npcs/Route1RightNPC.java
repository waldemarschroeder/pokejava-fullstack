package com.pokejava.npcs;

import com.pokejava.*;
import com.pokejava.pokejavas.Normie;

public class Route1RightNPC extends NPC {

    // Override
    @Override
    public InteractionInfo interacted(String userAnswer, NPC trainer) { 
        Battle battle = null;
        String npcAnswer = "";
        String[] possibleUserAnswers = null;
        
        if (!defeated) {
            switch(userAnswer) {
                // second answer
                case "yes": npcAnswer = "Yeah, let's go!"; battle = new Battle(trainer, this); break;

                // second answer
                case "no": npcAnswer = "I don't care, we will fight nevertheless!"; battle = new Battle(trainer, this); break;
                
                // first answer
                default: 
                    npcAnswer = "I heard, that there is a big city in the near. Do you want to fight?"; 
                    possibleUserAnswers = new String[] {"yes", "no"};
                    break;
            }
        } else {
            switch(userAnswer) {
                // second answer
                case "yes": npcAnswer = "I will win this time!"; battle = new Battle(trainer, this); healPokes(); break;

                // second answer
                case "no": npcAnswer = "Yeah, you know, that you will lose!"; battle = null; break;
                
                // first answer
                default: 
                    npcAnswer = "You want to fight again?"; 
                    possibleUserAnswers = new String[] {"yes", "no"};
                    break;
            }
        }

        return new InteractionInfo(npcAnswer, possibleUserAnswers, battle);

    }

    public Route1RightNPC(Position p) { super("Jack", p, new PokeJava[]{ new Normie(7),}); }

}
