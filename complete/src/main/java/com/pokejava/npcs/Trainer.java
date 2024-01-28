package com.pokejava.npcs;

import java.util.ArrayList;
import java.util.List;

import com.pokejava.*;

public class Trainer extends NPC {

    public Trainer(String name, Position p, PokeJava[] pokes) { super(name, p, pokes); }

    //private List<String> badges = new ArrayList<>();

    // Function to add a badge to the list
    //public void addBadge(String badge) {
    //    badges.add(badge);
    //}

    // Function to return the badges
    //public List<String> getBadges() {
    //    return badges;
    //}

    private int numBadges = 0;
    public void addBadge() { this.numBadges++; }
    public int getNumBadges() { return numBadges; }
}

