package com.pokejava;

public class NPC {

    private String name;
    public String getName() { return this.name; }

    protected Position p;
    public Position getPos() { return this.p;}
    public void setPos(Position p) { this.p = p;}

    // max 6 pokejavas
    private PokeJava[] pokes = new PokeJava[6];
    public PokeJava[] getPokes() { return this.pokes; }
    public void healPokes() { 
        for (PokeJava poke : pokes) { poke.recHp(); }
    }

    protected boolean defeated = false;
    public boolean getDefeated() { return defeated; }
    public void setdefeated(boolean defeated) { this.defeated = defeated; }

    public record InteractionInfo(String npcAnswer, String[] possibleUserAnswers, boolean battle, NPC npc) {};
    
    // Override
    public InteractionInfo interacted(String userAnswer, PokeJava[] pokes) { return new InteractionInfo("hi", null, false, this); }

    //Override
    public void autoAction(Map map) {}

    public boolean move(Map map, String direction) {
        Position target = map.getTargetPosition(this.p, direction);
        if (map.mayMove(target)) {
            map.moveObj(this.p, target);
            this.p = target;
            return true;
        } else { return false; }
    }

    public NPC(Position p) { this.p = p; }

    public NPC(String name, Position p, PokeJava[] pokes) {
        this.name = name;
        this.p = p;
        this.pokes = pokes;
    }

    public static NPC createNpcFromClass(Class<? extends NPC> npcClass) {
        try {
            return npcClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error creating NPC from class " + npcClass, e);
        }
    }

}
