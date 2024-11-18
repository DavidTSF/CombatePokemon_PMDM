package dev.davveg.combatepokemon.viewmodel;

import dev.davveg.combatepokemon.pokemon.Pokemon;

public class PokemonBattleModel {

    public static class BattleGround {
        Pokemon attacker;
        Pokemon defender;
        public BattleGround(Pokemon attacker, Pokemon defender) {
            this.attacker = attacker;
            this.defender = defender;
        }
        public void resolve() {
            defender.setHp(defender.getHp() - attacker.getAtaque());
        }

    }

    public interface Callback {
        void acabarAtaque(Pokemon attacker, Pokemon defender);
    }


    public void attackPokemon (BattleGround bg, Callback callback) {
        bg.resolve();
        callback.acabarAtaque(bg.attacker, bg.defender);
    }




}
