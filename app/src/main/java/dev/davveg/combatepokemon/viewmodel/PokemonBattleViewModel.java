package dev.davveg.combatepokemon.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dev.davveg.combatepokemon.Pokemon;

public class PokemonBattleViewModel extends AndroidViewModel {

    Executor executor;

    MutableLiveData<Pokemon> pokemon1 = new MutableLiveData<>();
    MutableLiveData<Pokemon> pokemon2 = new MutableLiveData<>();

    public PokemonBattleViewModel(@NonNull Application application) {
        super(application);

        executor = Executors.newSingleThreadExecutor();

    }




}
