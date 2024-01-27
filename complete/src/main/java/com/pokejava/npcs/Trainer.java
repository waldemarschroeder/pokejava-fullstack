package com.pokejava.npcs;

import java.util.ArrayList;
import java.util.List;

import com.pokejava.*;

public class Trainer extends NPC {

    private List<String> badges = new ArrayList<>();

    public Trainer(String name, Position p, PokeJava[] pokes) { super(name, p, pokes); }

    // Function to add a badge to the list
    public void addBadge(String badge) {
        badges.add(badge);
    }

    // Function to return the badges
    public List<String> getBadges() {
        return badges;
    }

}

