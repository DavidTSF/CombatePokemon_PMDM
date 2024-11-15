package dev.davveg.combatepokemon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import dev.davveg.combatepokemon.databinding.FragmentPokemonBattleBinding;


public class PokemonBattleFragment extends Fragment {

    private FragmentPokemonBattleBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentPokemonBattleBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(PokemonBattleFragment.this).load(R.drawable.alakazam_espalda).into(binding.pokemonIzquierda);
        Glide.with(PokemonBattleFragment.this).load(R.drawable.arbok).into(binding.pokemonDerecha);



    }
}