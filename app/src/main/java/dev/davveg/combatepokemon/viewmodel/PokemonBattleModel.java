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
    }

    public interface Callback {
        void finishAttack(Pokemon attacker, Pokemon defender);
        void battleFinished(Pokemon attacker, Pokemon defender);
        void pokemonLowHp(Pokemon pokemonLowHp);
        void notifyIsNormalAttack(boolean isNormalAttack, Pokemon attacker);
    }

    public void attackPokemon (BattleGround bg, Callback callback) {
        boolean willBeNormalAttack = willBeNormalAttack();
        int danio = 0;
        if ( willBeNormalAttack ) {
            danio = (int) (bg.K * bg.attacker.getAtaque() / (bg.K + bg.defender.getDefensa()));
            callback.notifyIsNormalAttack(true, bg.attacker);
        } else {
            danio = (int) (bg.K * bg.attacker.getAtaqueEspecial() / (bg.K + bg.defender.getDefensaEspecial()));
            callback.notifyIsNormalAttack(false, bg.attacker);
        }

        int porcentajeVida = (int) (100 - ( ( (double) (bg.defender.getMaxHp() - bg.defender.getHp()) / bg.defender.getMaxHp() ) * 100 ));

        if (bg.defender.getHp() <= danio) {
            bg.defender.setHp(0);
            callback.battleFinished(bg.attacker, bg.defender);
        } else {
            if ( porcentajeVida < 25 ) {
                if ( !bg.defender.isAlreadyLow() ) {
                    callback.pokemonLowHp(bg.defender);
                    bg.defender.setAlreadyLow(true);
                }

            }
            bg.defender.setHp(bg.defender.getHp() - danio);
        }


        callback.finishAttack(bg.attacker, bg.defender);
    }


    private static boolean willBeNormalAttack() {
        return random.nextBoolean();
    }

}
