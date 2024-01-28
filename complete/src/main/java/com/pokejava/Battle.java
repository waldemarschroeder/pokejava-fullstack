package com.pokejava;

import com.pokejava.PokeJava.PokeInfo;

public class Battle {

    private final NPC trainer;
    private NPC npc;

    private PokeJava trainerPoke;
    private PokeJava npcPoke;
    
    private String[][] text;
    private boolean trainerPokeStarts = true;
    private boolean active = true;
    private boolean wildPoke = false;

    public record BattleInfo(PokeInfo trainerPoke, PokeInfo npcPoke, String[][] text, boolean trainerPokeStarts, boolean active, boolean wildPoke) {}
    public BattleInfo getBattleInfo() {
        if (active) {
            return new BattleInfo(
                trainerPoke.getPokeInfo(),
                npcPoke.getPokeInfo(),
                this.text,
                this.trainerPokeStarts,
                this.active,
                this.wildPoke
            );
        } else {
            return new BattleInfo(
                null,
                null,
                this.text,
                this.trainerPokeStarts,
                this.active,
                this.wildPoke
            );
        }
    }

    // trainer vs. npc
    public Battle(NPC trainer, NPC npc) {
        this.trainer = trainer;
        trainer.setMayMove(false);
        this.npc = npc;

        this.trainerPoke = findFirstFightablePoke(trainer.getPokes());
        this.npcPoke = findFirstFightablePoke(npc.getPokes());

        this.text = new String[][] { 
            new String[] {
                trainer.getName() + " fights against " + npc.getName(),
                "Let's go " + trainerPoke.getPokeInfo().specie()
            },
        };
    }

    // trainer vs. wildPoke
    public Battle(NPC trainer, PokeJava wildPoke) {
        this.wildPoke = true;
        this.trainer = trainer;
        trainer.setMayMove(false);

        this.trainerPoke = findFirstFightablePoke(trainer.getPokes());
        this.npcPoke = wildPoke;

        this.text = new String[][] { 
            new String[] {
                "A wild " + npcPoke.getPokeInfo().specie() + " has come!",
                "Let's go " + trainerPoke.getPokeInfo().specie()
            },
        };
    }

    private PokeJava findFirstFightablePoke(PokeJava[] pokes) {
        for (PokeJava poke : pokes) {
            if (poke.getPokeInfo().isHp() > 0) { return poke; }
        }
        // Error
        return null;
    }

    public void fight(int trainerChoice) {

        // if not active stop
        if (!this.active) { return; }

        int npcChoice = npcPoke.getIndexBestAttack(trainerPoke);

        //PokeInfo trainerPokeInfo = trainerPoke.getPokeInfo();
        //PokeInfo npcPokeInfo = npcPoke.getPokeInfo();

        String[] texts1 = null;
        String[] texts2 = null;
        String[] texts3 = null;

        // the faster poke will start
        if (trainerPoke.getPokeInfo().stats().speed() >= npcPoke.getPokeInfo().stats().speed()) {
            this.trainerPokeStarts = true;
            texts1 = trainerPoke.attackPoke(trainerChoice, npcPoke);
            if (npcPoke.getPokeInfo().isHp() > 0) { 
                texts2 = npcPoke.attackPoke(npcChoice, trainerPoke);
            }
        } else {
            this.trainerPokeStarts = false;
            texts1 = npcPoke.attackPoke(npcChoice, trainerPoke);
            if (trainerPoke.getPokeInfo().isHp() > 0) {
                texts2 = trainerPoke.attackPoke(trainerChoice, npcPoke);
            }
        }

        if (trainerPoke.getPokeInfo().isHp() == 0) {
            this.trainerPoke = findFirstFightablePoke(this.trainer.getPokes());
            // battle is over
            if (trainerPoke == null) { 
                battleEnded();
                texts3 = new String[] { "You lost" };
            }
        }
        if (npcPoke.getPokeInfo().isHp() == 0) {
            trainerPoke.incExp(4*npcPoke.getPokeInfo().lvl());
            if (!wildPoke) { this.npcPoke = findFirstFightablePoke(this.npc.getPokes()); }
            else { npcPoke = null; }
            // battle is over
            if (npcPoke == null) { 
                battleEnded();
                texts3 = new String[] { "You won" };
                if (!wildPoke) { npc.setdefeated(true); }
            }
        }

        this.text = new String[][] { texts1, texts2, texts3 };

    }

    public boolean tryEscape() {
        if (wildPoke) {
            battleEnded();
            return true;
        }
        // trainer cannot escape npc battle
        else { return false; }
    }

    private void battleEnded() {
        this.active = false;
        trainer.setMayMove(true);
    }
}

