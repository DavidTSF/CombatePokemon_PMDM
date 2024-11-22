package dev.davveg.combatepokemon;

import static com.google.android.material.color.MaterialColors.getColor;


import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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

        final PokemonBattleViewModel pbViewModel = new ViewModelProvider(this).get(PokemonBattleViewModel.class);

        Pokemon rightPokemon;
        Pokemon leftPokemon;

        ProgressBar progressBarLeft = binding.progressBarLeft;
        ProgressBar progressBarRight = binding.progressBarRight;

        if ( pbViewModel.getPokemon_right().isInitialized() && pbViewModel.getPokemon_left().isInitialized() ) {
            leftPokemon = pbViewModel.getPokemon_left().getValue();
            rightPokemon = pbViewModel.getPokemon_right().getValue();
        } else {
            rightPokemon = new Pokemon("Arbok", 600,120,50,50,50);
            leftPokemon = new Pokemon("Alakazam", 500,80,50,50,50);
        }

        binding.pokemonLeftHP.setText( String.valueOf(leftPokemon.getHp()) );
        binding.pokemonRightHP.setText( String.valueOf(rightPokemon.getHp()) );

        binding.pokemonRightAttack.setText( String.valueOf( rightPokemon.getAtaque() ) );
        binding.pokemonRightDefense.setText( String.valueOf( rightPokemon.getDefensa() ) );
        binding.pokemonRightExAttack.setText( String.valueOf( rightPokemon.getAtaqueEspecial() ) );
        binding.pokemonRightExDefense.setText( String.valueOf( rightPokemon.getDefensa() ) );
        binding.pokemonRightName.setText( String.valueOf( rightPokemon.getNombre() ) );

        binding.pokemonLeftAttack.setText( String.valueOf( leftPokemon.getAtaque() ) );
        binding.pokemonLeftDefense.setText( String.valueOf( leftPokemon.getDefensa() ) );
        binding.pokemonLeftExAttack.setText( String.valueOf( leftPokemon.getAtaqueEspecial() ) );
        binding.pokemonLeftExDefense.setText( String.valueOf( leftPokemon.getDefensa() ) );
        binding.pokemonLeftName.setText( String.valueOf( leftPokemon.getNombre() ) );

        progressBarLeft.setMax(leftPokemon.getMaxHp());
        progressBarLeft.setProgress(leftPokemon.getHp());
        progressBarRight.setMax(rightPokemon.getMaxHp());
        progressBarRight.setProgress(rightPokemon.getHp());


        binding.pokemonLeftButton.setOnClickListener(
                view1 -> pbViewModel.attack(leftPokemon, rightPokemon, PokemonBattleViewModel.ATTACK_D.LEFT_TO_RIGHT)
        );
        pbViewModel.getPokemon_left().observe(getViewLifecycleOwner(), new Observer<Pokemon>() {
                    @Override
                    public void onChanged(Pokemon pokemon) {
                        binding.pokemonLeftHP.setText( String.valueOf( pokemon.getHp() ) );
                        reduceBar(progressBarLeft, pokemon, view);



                    }
                });

        binding.pokemonRightButton.setOnClickListener(
                view12 -> pbViewModel.attack(rightPokemon, leftPokemon, PokemonBattleViewModel.ATTACK_D.RIGHT_TO_LEFT)
        );

        pbViewModel.getPokemon_right().observe(getViewLifecycleOwner(), new Observer<Pokemon>() {
                    @Override
                    public void onChanged(Pokemon pokemon) {
                        binding.pokemonRightHP.setText( String.valueOf( pokemon.getHp() ) );
                        reduceBar(progressBarRight, pokemon, view);


                    }
                });


    }


    @SuppressLint("ResourceType")
    public void reduceBar(ProgressBar pb, Pokemon p, View view) {
        int porcentajeVida = (int) (100 - ( ( (double) (p.getMaxHp() - p.getHp()) / p.getMaxHp() ) * 100 ));
        if (porcentajeVida < 25) {
            pb.setProgressTintList(ColorStateList.valueOf(Color.RED));
        } else if (porcentajeVida < 50) {
            pb.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#ffa500")));
        }
        pb.setProgress(p.getHp());
    }

}