package dev.davveg.combatepokemon.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dev.davveg.combatepokemon.pokemon.Pokemon;

public class PokemonBattleViewModel extends AndroidViewModel {

    Executor executor;
    PokemonBattleModel pokemonBattleModel;

    MutableLiveData<Pokemon> pokemon_left = new MutableLiveData<>();
    MutableLiveData<Pokemon> pokemon_right = new MutableLiveData<>();

    public PokemonBattleViewModel(@NonNull Application application) {
        super(application);
        executor = Executors.newSingleThreadExecutor();
        pokemonBattleModel = new PokemonBattleModel();
    }

    public void attackLtoR(Pokemon attacker, Pokemon defender) {
        final PokemonBattleModel.BattleGround battleGround = new PokemonBattleModel.BattleGround(attacker, defender);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                pokemonBattleModel.attackPokemon(battleGround, new PokemonBattleModel.Callback() {
                    @Override
                    public void acabarAtaque(Pokemon attacker, Pokemon defender) {
                        pokemon_left.postValue(attacker);
                        pokemon_right.postValue(defender);
                    }
                });
            }
        });

    }

    public void attackRtoL(Pokemon attacker, Pokemon defender) {
        final PokemonBattleModel.BattleGround battleGround = new PokemonBattleModel.BattleGround(attacker, defender);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                pokemonBattleModel.attackPokemon(battleGround, new PokemonBattleModel.Callback() {
                    @Override
                    public void acabarAtaque(Pokemon attacker, Pokemon defender) {
                        pokemon_left.postValue(defender);
                        pokemon_right.postValue(attacker);


                    }
                });
            }
        });

    }



    public MutableLiveData<Pokemon> getPokemon_left() {
        return pokemon_left;
    }

    public MutableLiveData<Pokemon> getPokemon_right() {
        return pokemon_right;
    }
}
