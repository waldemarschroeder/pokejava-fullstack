package com.pokejava;

import com.pokejava.PokeJava.PokeInfo;

public class Battle {

    private final Trainer trainer;
    private final NPC npc;
    private int indexFightingTrainerPoke;
    private int indexFightingNpcPoke;
    private String[][] text;
    private boolean trainerPokeStarts = true;
    private boolean active;

    public record BattleInfo(PokeInfo trainerPoke, PokeInfo npcPoke, String[][] text, boolean trainerPokeStarts, boolean active) {}
    public BattleInfo getBattleInfo() {
        return new BattleInfo(
            this.trainer.getPokes()[indexFightingTrainerPoke].getPokeInfo(),
            this.npc.getPokes()[indexFightingNpcPoke].getPokeInfo(),
            this.text,
            this.trainerPokeStarts,
            this.active
        );
    }

    public Battle(Trainer trainer, NPC npc) {
        this.trainer = trainer;
        this.npc = npc;

        this.indexFightingTrainerPoke = findIndexFirstFightablePoke(this.trainer.getPokes());
        this.indexFightingNpcPoke = findIndexFirstFightablePoke(this.npc.getPokes());

        initializeBattleText();
        this.active = true;
    }

    private void initializeBattleText() {
        this.text = new String[][] { 
            new String[] {
                trainer.getName() + " fights against " + npc.getName(),
                "Let's go " + trainer.getPokes()[indexFightingTrainerPoke].getSpecie()
            },
        };
    }

    private int findIndexFirstFightablePoke(PokeJava[] pokes) {
        for (int i = 0; i < pokes.length; i++) {
            PokeJava poke = pokes[i];
            if (poke.getIsHp() > 0) {
                return i;
            }
        }
        // Error
        return 100;
    }

    public BattleInfo fight(int trainerChoice) {

        // if not active stop
        if (!this.active) { return new BattleInfo(null, null, text, trainerPokeStarts, active); }

        PokeJava trainerPoke = trainer.getPokes()[indexFightingTrainerPoke];
        PokeJava npcPoke = npc.getPokes()[indexFightingNpcPoke];

        int npcChoice = npcPoke.getIndexBestAttack(trainerPoke);

        String[] texts1 = null;
        String[] texts2 = null;
        String[] texts3 = null;

        // the faster poke will start
        if (trainerPoke.getStats().speed() >= npcPoke.getStats().speed()) {
            this.trainerPokeStarts = true;
            texts1 = trainerPoke.attackPoke(trainerChoice, npcPoke);
            if (npcPoke.getIsHp() > 0) { 
                texts2 = npcPoke.attackPoke(npcChoice, trainerPoke);
            }
        } else {
            this.trainerPokeStarts = false;
            texts1 = npcPoke.attackPoke(npcChoice, trainerPoke);
            if (trainerPoke.getIsHp() > 0) {
                texts2 = trainerPoke.attackPoke(trainerChoice, npcPoke);
            }
        }

        if (trainerPoke.getIsHp() == 0) {
            int index = findIndexFirstFightablePoke(this.trainer.getPokes());
            // battle is over
            if (index == 100) { 
                this.active = false;
                texts3 = new String[] { "You lost" };
            } else {
                // next poke
                this.indexFightingTrainerPoke = index;
            }
        }
        if (npcPoke.getIsHp() == 0) {
            int index = findIndexFirstFightablePoke(this.npc.getPokes());
            // battle is over
            if (index == 100) { 
                this.active = false; 
                texts3 = new String[] { "You won" };
                npc.setdefeated(true);
            } else {
                // next poke
                this.indexFightingNpcPoke = index;
            }
        }

        this.text = new String[][] { texts1, texts2, texts3 };

        return getBattleInfo();
    }

    public boolean tryEscape() {
        this.active = false;
        return true;
    }
}

