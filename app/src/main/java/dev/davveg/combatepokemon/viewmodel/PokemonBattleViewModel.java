package dev.davveg.combatepokemon.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dev.davveg.combatepokemon.pokemon.Pokemon;

public class PokemonBattleViewModel extends AndroidViewModel {

    public static class BattleStatus {
        public Pokemon winner;
        public Pokemon losser;
        public boolean finished;

        public BattleStatus(Pokemon winner, Pokemon losser, boolean finished) {
            this.winner = winner;
            this.losser = losser;
            this.finished = finished;
        }
    }


    Executor executor;
    PokemonBattleModel pokemonBattleModel;

    MutableLiveData<Pokemon> pokemon_left = new MutableLiveData<>();
    MutableLiveData<Pokemon> pokemon_right = new MutableLiveData<>();
    MutableLiveData<BattleStatus> battleStatus = new MutableLiveData<>();
    MutableLiveData<Pokemon> pokemonLowHp = new MutableLiveData<>();

    MutableLiveData<Boolean> isNormalAttack = new MutableLiveData<>();
    MutableLiveData<Pokemon> pokemonCurrentAttacker = new MutableLiveData<>();

    public enum ATTACK_D {
        RIGHT_TO_LEFT,
        LEFT_TO_RIGHT,
    }

    public PokemonBattleViewModel(@NonNull Application application) {
        super(application);
        executor = Executors.newSingleThreadExecutor();
        pokemonBattleModel = new PokemonBattleModel();
    }

    public void attack(Pokemon attacker, Pokemon defender, ATTACK_D attackDir) {
        final PokemonBattleModel.BattleGround battleGround = new PokemonBattleModel.BattleGround(attacker, defender);
        executor.execute(() ->
            pokemonBattleModel.attackPokemon(battleGround, new PokemonBattleModel.Callback() {
                @Override
                public void acabarAtaque(Pokemon attacker, Pokemon defender) {
                    if (attackDir.equals(ATTACK_D.LEFT_TO_RIGHT)) {
                        pokemon_left.postValue(attacker);
                        pokemon_right.postValue(defender);
                    } else {
                        pokemon_left.postValue(defender);
                        pokemon_right.postValue(attacker);
                    }
                }
                @Override
                public void battleFinished(Pokemon attacker, Pokemon defender) {
                    defender.setAlreadyLow(false);
                    battleStatus.postValue( new BattleStatus(attacker, defender, true) );
                }
                @Override
                public void pokemonLowHp(Pokemon pokemonLow) {
                    pokemonLowHp.postValue(pokemonLow);
                }

                @Override
                public void notifyIsNormalAttack(boolean pIsNormalAttack, Pokemon attacker) {
                    pokemonCurrentAttacker.postValue(attacker);
                    isNormalAttack.postValue(pIsNormalAttack);
                }
            })
        );

    }



    public MutableLiveData<Pokemon> getPokemon_left() {
        return pokemon_left;
    }

    public MutableLiveData<Pokemon> getPokemon_right() {
        return pokemon_right;
    }

    public MutableLiveData<BattleStatus> getBattleFinished() {
        return battleStatus;
    }

    public MutableLiveData<Pokemon> getPokemonLowHp() {
        return pokemonLowHp;
    }

    public MutableLiveData<Boolean> getIsNormalAttack() {
        return isNormalAttack;
    }

    public MutableLiveData<Pokemon> getPokemonCurrentAttacker() {
        return pokemonCurrentAttacker;
    }
}
