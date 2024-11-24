package dev.davveg.combatepokemon.pokemon;

import java.io.Serializable;

public class Pokemon implements Serializable {
    String nombre;
    int hp;
    int maxHp;
    int ataque ,defensa;
    int ataqueEspecial ,defensaEspecial;

    boolean alreadyLow = false;


    public Pokemon() {
    }

    public Pokemon(String nombre, int hp, int ataque, int defensa, int ataqueEspecial, int defensaEspecial) {
        this.nombre = nombre;
        this.hp = hp;
        this.ataque = ataque;
        this.defensa = defensa;
        this.ataqueEspecial = ataqueEspecial;
        this.defensaEspecial = defensaEspecial;
        this.maxHp = hp;
    }


    public String getNombre() {
        return nombre;
    }

    public int getHp() {
        return hp;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getAtaqueEspecial() {
        return ataqueEspecial;
    }

    public int getDefensaEspecial() {
        return defensaEspecial;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public boolean isAlreadyLow() {
        return alreadyLow;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public void setAtaqueEspecial(int ataqueEspecial) {
        this.ataqueEspecial = ataqueEspecial;
    }

    public void setDefensaEspecial(int defensaEspecial) {
        this.defensaEspecial = defensaEspecial;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public void setAlreadyLow(boolean alreadyLow) {
        this.alreadyLow = alreadyLow;
    }
}
