package com.joshualorett.networksample.sample;

import com.google.gson.annotations.SerializedName;

/**
 * Sample data object of a "character".
 */

public class Character {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
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
