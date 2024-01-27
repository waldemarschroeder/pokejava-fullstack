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

    // the default npc doesnt want to interact and is blind
    protected boolean wantsToInteract = false;
    public boolean getWantsToInteract() { return this.wantsToInteract; }

    protected boolean trainerSeen = false;
    public boolean getTrainerSeen() { return this.trainerSeen; }
    public void setTrainerSeen(Position trainerPos) {}; // Override

    protected boolean mayMove = true;
    public boolean getMayMove() { return this.mayMove; }
    public void setMayMove(boolean mayMove) { this.mayMove = mayMove; }

    public record InteractionInfo(String npcAnswer, String[] possibleUserAnswers, boolean battle) {};
    
    // Override
    public InteractionInfo interacted(String userAnswer, Map map) { return new InteractionInfo("hi", null, false); }

    public boolean autoAction(Map map) { return false; } // Override

    public record MoveInfo(boolean success, boolean npcWantInteraction, boolean wildPokeAttacked) {}

    public MoveInfo move(Map map, String direction) {

        // update direction, always if it's a npc, maybe not if it's a trainer
        if (mayMove || this.getClass() != Trainer.class) { this.direction = direction; }

        // move blocked, stop here
        else { return new MoveInfo(false, false, false); }

        boolean success = false;
        boolean npcWantInteraction = false;
        boolean wildPokeAttacked = false;
        Position target = getTargetPosition();
        if (map.mayMove(target)) {
            map.moveObj(this.p, target);
            this.p = target;
            success = true;

            // the both other bools are only relevant for the trainer
            if (this.getClass() == Trainer.class) { 
                npcWantInteraction = map.npcWantInteraction();
                wildPokeAttacked = map.wildPokeAttacked();
            }
            
        }
        return new MoveInfo(success, npcWantInteraction, wildPokeAttacked);
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

    protected void findNextMove(Map map, Position currentPosition, Position targetPosition) {
        int dx = targetPosition.x() - currentPosition.x();
        int dy = targetPosition.y() - currentPosition.y();

        String direction;
        // Prioritize the axis with the bigger distance
        if (Math.abs(dx) > Math.abs(dy)) {
            // Try move horizontally first
            direction = (dx > 0) ? "right" : "left";
            if (move(map, direction).success()) {
                return; // Exit the method if the horizontal move is successful
            }
            // If not successful, try move vertically
            direction = (dy > 0) ? "down" : "up";
            move(map, direction);
        } else {
            // Try move vertically first
            direction = (dy > 0) ? "down" : "up";
            if (move(map, direction).success()) {
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
