package com.pokejava;

public class Trainer {

    private String name;
    public String getName() { return this.name; }
    
    // max 6 pokejavas
    private PokeJava[] pokes = new PokeJava[6];
    public PokeJava[] getPokes() { return this.pokes; }
    public void setPokes(PokeJava[] pokes) { this.pokes = pokes;}
    public void changePokesOrder(int oldIndex, int newIndex) { 
        if (oldIndex < 0 || newIndex < 0 || oldIndex >= pokes.length || newIndex >= pokes.length) {
            System.err.println("Invalid indices. Indices should be within the list bounds.");
            return;
        }

        PokeJava temp = pokes[oldIndex];
        pokes[oldIndex] = pokes[newIndex];
        pokes[newIndex] = temp;

    }

    public Trainer(String name, PokeJava[] pokes) {
        this.name = name;
        this.pokes = pokes;
    }
    
}
