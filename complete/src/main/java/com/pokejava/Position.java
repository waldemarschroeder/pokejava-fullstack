package com.pokejava;

//import java.util.Random;

public record Position(int y, int x) { 
    
    // at this time not necessary 
    /* 
    public Position randomPos (int min, int max) {
        if (min > max){
            System.out.println("Error: min cannot be > max");
            return new Position(0,0);
        }
        else {
            Random randx = new Random();
            // nextInt as provided by Random is exclusive of the top value so you need to add 1 
            int a = randx.nextInt((max - min) + 1) + min;
            Random randy = new Random();
            int b = randy.nextInt((max - min) + 1) + min;
            return new Position(a,b);
        }  
    }
    */
}
