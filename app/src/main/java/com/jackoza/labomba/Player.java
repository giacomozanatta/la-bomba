package com.jackoza.labomba;

public class Player {
    private String name;
    private int bombs;

    public Player(String name) {
        this.name = name;
        bombs = 0;
    }
    public String getName() {
        return this.name;
    }
    public int getBomb() {
        return this.bombs;
    }
    public void setBomb(int bombs) {
        this.bombs = bombs;
    }
    public void increaseBomb() {
        this.bombs++;
    }
    public void decreaseBomb() {
        if (this.bombs > 0) {
            this.bombs--;
        }
    }

}
