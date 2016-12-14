package com.joshualorett.networksample.sample;

/**
 * Sample data object of a "character".
 */

public class Character {
    private int id;

    private String name;

    public Character() {}

    public Character(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
