package com.firstjavafx;

import java.util.ArrayList;

public class Pokemon {
    String name;
    String region;
    String type;
    ArrayList<String> weaknesses = new ArrayList<>();;
    ArrayList<String> strengths = new ArrayList<>();;
    
    Pokemon() {
        name = "None";
        region = "None";
        type = "None";
    }
    
    Pokemon(String name, String region, String type) {
        this.name = name;
        this.region = region;
        this.type = type;

        this.setWeakAndStrength();
    }
    
    String getName() {
        return name;
    }

    String getRegion() {
        return region;
    }
    
    String getType() {
        return type;
    }

    ArrayList<String> getWeaknesses() {
        return weaknesses;
    }

    ArrayList<String> getStrengths() {
        return strengths;
    }

    void setName(String name) {
        this.name = name;
    }

    void setRegion(String region) {
        this.region = region;
    }

    void setType(String type) {
        this.type = type;
    }
    

    void setWeakAndStrength() {
        if(type.equalsIgnoreCase("Normal")) {
            weaknesses.add("Fighting");
            
        } else if(type.equalsIgnoreCase("Fire")) {
            weaknesses.add("Ground"); weaknesses.add("Water"); weaknesses.add("Rock");
            strengths.add("Bug"); strengths.add("Steel"); strengths.add("Grass"); strengths.add("Ice");
                    
        } else if(type.equalsIgnoreCase("Water")) {
            strengths.add("Ground"); strengths.add("Fire"); strengths.add("Rock");
            weaknesses.add("Grass"); weaknesses.add("Electric");
            
        } else if(type.equalsIgnoreCase("Grass")) {
            strengths.add("Ground"); strengths.add("Water"); strengths.add("Rock");
            weaknesses.add("Flying"); weaknesses.add("Poison"); weaknesses.add("Bug"); weaknesses.add("Fire"); weaknesses.add("Ice");
            
        } else if(type.equalsIgnoreCase("Flying")) {
            strengths.add("Fighting"); strengths.add("Bug"); strengths.add("Grass"); 
            weaknesses.add("Rock"); weaknesses.add("Electric"); weaknesses.add("Ice");
            
        } else if(type.equalsIgnoreCase("Fighting")) {
            strengths.add("Steel"); strengths.add("Rock"); strengths.add("Normal"); strengths.add("Ice"); strengths.add("Dark");
            weaknesses.add("Flying"); weaknesses.add("Psychic"); weaknesses.add("Fairy");
            
        } else if(type.equalsIgnoreCase("Poison")) {
            strengths.add("Grass"); strengths.add("Fairy"); 
            weaknesses.add("Ground"); weaknesses.add("Psychic");
            
        } else if(type.equalsIgnoreCase("Electric")) {
            strengths.add("Water"); strengths.add("Flying");
            weaknesses.add("Ground");
            
        } else if(type.equalsIgnoreCase("Ground")) {
            strengths.add("Steel"); strengths.add("Rock"); strengths.add("Fire"); strengths.add("Electric"); strengths.add("Poison");
            weaknesses.add("Water"); weaknesses.add("Grass"); weaknesses.add("Ice");
            
        } else if(type.equalsIgnoreCase("Rock")) {
            strengths.add("Flying"); strengths.add("Bug"); strengths.add("Fire"); strengths.add("Ice");
            weaknesses.add("Water"); weaknesses.add("Grass"); weaknesses.add("Fighting"); weaknesses.add("Ground"); weaknesses.add("Steel");
            
        } else if(type.equalsIgnoreCase("Psychic")) {
            strengths.add("Fighting"); strengths.add("Poison"); 
            weaknesses.add("Bug"); weaknesses.add("Ghost"); weaknesses.add("Dark");
            
        } else if(type.equalsIgnoreCase("Ice")) {
            strengths.add("Flying"); strengths.add("Grass"); strengths.add("Ground"); strengths.add("Dragon");
            weaknesses.add("Rock"); weaknesses.add("Fire"); weaknesses.add("Fighting"); weaknesses.add("Steel");
            
        } else if(type.equalsIgnoreCase("Bug")) {
            strengths.add("Grass"); strengths.add("Psychic"); strengths.add("Dark");
            weaknesses.add("Rock"); weaknesses.add("Fire"); weaknesses.add("Flying");
            
        } else if(type.equalsIgnoreCase("Ghost")) {
            strengths.add("Ghost"); strengths.add("Psychic");
            weaknesses.add("Ghost"); weaknesses.add("Dark");
            
        } else if(type.equalsIgnoreCase("Steel")) {
            strengths.add("Ice"); strengths.add("Rock"); strengths.add("Fairy");
            weaknesses.add("Fighting"); weaknesses.add("Fire"); weaknesses.add("Ground");
            
        } else if(type.equalsIgnoreCase("Dragon")) {
            strengths.add("Dragon");
            weaknesses.add("Ice"); weaknesses.add("Dragon"); weaknesses.add("Fairy");
            
        } else if(type.equalsIgnoreCase("Dark")) {
            strengths.add("Ghost"); strengths.add("Psychic");
            weaknesses.add("Fighting"); weaknesses.add("Bug"); weaknesses.add("Fairy");
            
        } else if(type.equalsIgnoreCase("Fairy")) {
            strengths.add("Fighting"); strengths.add("Dragon"); strengths.add("Dark");
            weaknesses.add("Poison"); weaknesses.add("Steel");
        }
    }

}
