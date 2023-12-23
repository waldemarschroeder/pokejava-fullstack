package com.pokejava;

import com.pokejava.npcs.Trainer;

public class NPC {

    private String name;
    public String getName() { return this.name; }

    protected Position p;
    public Position getPos() { return this.p;}
    public void setPos(Position p) { this.p = p;}

    protected Position initPos;

    protected String direction = "up"; // "up" init value
    public String getDirection() { return this.direction; }
    public void setDirection(String direction) { this.direction = direction; }

    // max 6 pokejavas
    private PokeJava[] pokes = new PokeJava[6];
    public PokeJava[] getPokes() { return this.pokes; }
    public void healPokes() { 
        for (PokeJava poke : pokes) { poke.recHp(); }
    }
    public void changePokesOrder(int oldIndex, int newIndex) { 
        if (oldIndex < 0 || newIndex < 0 || oldIndex >= pokes.length || newIndex >= pokes.length) {
            System.err.println("Invalid indices. Indices should be within the list bounds.");
            return;
        }

        PokeJava temp = pokes[oldIndex];
        pokes[oldIndex] = pokes[newIndex];
        pokes[newIndex] = temp;

    }

    protected boolean defeated = false;
    public boolean getDefeated() { return defeated; }
    public void setdefeated(boolean defeated) { this.defeated = defeated; }

    protected boolean trainerContacted = false;
    protected boolean trainerSeen = false;
    // Override
    public void setTrainerSeen(Map map) {};

    public record InteractionInfo(String npcAnswer, String[] possibleUserAnswers, Battle battle) {};
    
    // Override
    public InteractionInfo interacted(String userAnswer, NPC trainer) { return new InteractionInfo("hi", null, null); }

    //Override
    public InteractionInfo autoAction(Map map) { return null; }

    public boolean move(Map map, String direction) {

        // update direction, always if it's a npc, maybe not if it's a trainer
        if (map.getTrainerMayMove() || this.getClass() != Trainer.class) { this.direction = direction; }
        else { return false; }

        Position target = getTargetPosition();
        if (map.mayMove(target)) {
            map.moveObj(this.p, target);
            this.p = target;
            if (this.getClass() == Trainer.class) { map.npcsTrainerSeen(); }
            return true;
        } else { return false; }
    }

    public Position getTargetPosition() { 
        Position target;
        switch(direction) {
            case "left": target = new Position(p.y(), p.x()-1); break;
            case "right": target = new Position(p.y(), p.x()+1); break;
            case "up": target = new Position(p.y()-1, p.x()); break;
            case "down": target = new Position(p.y()+1, p.x()); break;
            default: target = p; break;
        }
        return target;
    }

    public NPC(Position p) { this.p = p; this.initPos = p; }

    public NPC(String name, Position p, PokeJava[] pokes) {
        this.name = name;
        this.p = p;
        this.initPos = p;
        this.pokes = pokes;
    }

    public static NPC createNpcFromClass(Class<? extends NPC> npcClass) {
        try {
            return npcClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error creating NPC from class " + npcClass, e);
        }
    }

    protected void findNextMove(Map map, Position currentPosition, Position targetPosition) {
        int dx = targetPosition.x() - currentPosition.x();
        int dy = targetPosition.y() - currentPosition.y();

        String direction;
        // Prioritize the axis with the bigger distance
        if (Math.abs(dx) > Math.abs(dy)) {
            // Try move horizontally first
            direction = (dx > 0) ? "right" : "left";
            if (move(map, direction)) {
                return; // Exit the method if the horizontal move is successful
            }
            // If not successful, try move vertically
            direction = (dy > 0) ? "down" : "up";
            move(map, direction);
        } else {
            // Try move vertically first
            direction = (dy > 0) ? "down" : "up";
            if (move(map, direction)) {
                return; // Exit the method if the vertical move is successful
            }
            // If not successful, try move horizontally
            direction = (dx > 0) ? "right" : "left";
            move(map, direction);
        }

    }

    protected void setDirectionToNpc(NPC npc) {
        int dx = npc.getPos().x() - p.x();
        int dy = npc.getPos().y() - p.y();
    
        String direction;
        if (Math.abs(dx) > Math.abs(dy)) {
            // Move horizontally
            direction = (dx < 0) ? "right" : "left";
        } else {
            // Move vertically
            direction = (dy < 0) ? "down" : "up";
        }
        npc.setDirection(direction);
    }

}
