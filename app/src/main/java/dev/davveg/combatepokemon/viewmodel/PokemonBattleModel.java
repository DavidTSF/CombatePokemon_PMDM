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
        //Constante para el balance de la defensa
        double K = 100;

        public void resolve() {


            int danio = (int) (K * attacker.getAtaque() / (K + defender.getDefensa()));
            if (defender.getHp() <= danio) {
                defender.setHp(0);
            } else {
                defender.setHp(defender.getHp() - danio);
            }







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
