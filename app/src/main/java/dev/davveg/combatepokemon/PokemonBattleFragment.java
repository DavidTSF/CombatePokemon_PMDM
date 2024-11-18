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
import dev.davveg.combatepokemon.pokemon.Pokemon;
import dev.davveg.combatepokemon.viewmodel.PokemonBattleViewModel;


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


        final PokemonBattleViewModel pokemonBattleViewModel = new ViewModelProvider(this).get(PokemonBattleViewModel.class);

        Pokemon rightPokemon = new Pokemon("Arbok", 200,100,50,50,50);
        Pokemon leftPokemon = new Pokemon("Alakazam", 200,100,50,50,50);





        binding.pokemonLeftHP.setText( String.valueOf(leftPokemon.getHp()) );
        binding.pokemonRightHP.setText( String.valueOf(rightPokemon.getHp()) );


        binding.pokemonLeftButton.setOnClickListener(
                new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                        pokemonBattleViewModel.attackLtoR(leftPokemon, rightPokemon);
                     }
                 }
        );

        pokemonBattleViewModel
                .getPokemon_left().observe(getViewLifecycleOwner(), new Observer<Pokemon>() {
                    @Override
                    public void onChanged(Pokemon pokemon) {
                        binding.pokemonLeftHP.setText( String.valueOf( pokemon.getHp() ) );
                    }
                });

        binding.pokemonRightButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pokemonBattleViewModel.attackRtoL(rightPokemon, leftPokemon);
                    }
                }
        );

        pokemonBattleViewModel
                .getPokemon_right().observe(getViewLifecycleOwner(), new Observer<Pokemon>() {
                    @Override
                    public void onChanged(Pokemon pokemon) {
                        binding.pokemonRightHP.setText( String.valueOf( pokemon.getHp() ) );
                    }
                });


    }
}