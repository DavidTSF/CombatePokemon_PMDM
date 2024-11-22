package dev.davveg.combatepokemon.viewmodel;

import java.util.Random;

import dev.davveg.combatepokemon.pokemon.Pokemon;

public class PokemonBattleModel {
    private static final Random random = new Random();

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
            int danio = 0;
            if ( willBeNormalAttack() ) {
                danio = (int) (K * attacker.getAtaque() / (K + defender.getDefensa()));
            } else {
                danio = (int) (K * attacker.getAtaqueEspecial() / (K + defender.getDefensaEspecial()));
            }

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


    private static boolean willBeNormalAttack() {
        return random.nextBoolean();
    }

}
