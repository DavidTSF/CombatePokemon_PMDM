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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

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

        PokemonBattleViewModel pbViewModel = new ViewModelProvider(requireActivity()).get(PokemonBattleViewModel.class);

        Pokemon rightPokemon = pbViewModel.getPokemon_right().getValue();
        Pokemon leftPokemon = pbViewModel.getPokemon_left().getValue();

        ProgressBar progressBarLeft = binding.progressBarLeft;
        ProgressBar progressBarRight = binding.progressBarRight;

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

        // Comprueba si el combate a acabado y si si, devuelve al usuario al homeFragment despues de 6 segundos
        pbViewModel.getBattleFinished().observe( getViewLifecycleOwner(), battleStatus -> {
            if (battleStatus.finished) {
                // Si ha acabado el combate le enviamos un mensaje y volveremos al fragmento home para volver a empezaar otro combate
                Snackbar snackbar = Snackbar.make(view, "Combate a acabado, ha ganado: " + battleStatus.winner.getNombre()
                        + ". En 6 segundos volveras al home", Snackbar.LENGTH_LONG);
                snackbar.show();
                binding.pokemonRightButton.setOnClickListener(null);
                binding.pokemonLeftButton.setOnClickListener(null);

                Handler mainLooperHandler = new Handler(Looper.getMainLooper());
                mainLooperHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(getActivity()!=null){
                            NavController navController = Navigation.findNavController(view);
                            navController.navigate(R.id.action_pokemonBattle_to_homeFragment);
                        }
                    }
                }, 6000);

                battleStatus.finished = false;
                pbViewModel.getBattleFinished().postValue(battleStatus);
            }
        });


        binding.pokemonLeftButton.setOnClickListener(
                view1 -> pbViewModel.attack(leftPokemon, rightPokemon, PokemonBattleViewModel.ATTACK_D.LEFT_TO_RIGHT)
        );
        pbViewModel.getPokemon_left().observe(getViewLifecycleOwner(), new Observer<Pokemon>() {
            @Override
            public void onChanged(Pokemon pokemon) {
                binding.pokemonLeftHP.setText( String.valueOf( pokemon.getHp() ) );
                reduceBar(progressBarLeft, pokemon);
            }
        });

        binding.pokemonRightButton.setOnClickListener(
                view2-> pbViewModel.attack(rightPokemon, leftPokemon, PokemonBattleViewModel.ATTACK_D.RIGHT_TO_LEFT)
        );
        pbViewModel.getPokemon_right().observe(getViewLifecycleOwner(), new Observer<Pokemon>() {
                    @Override
                    public void onChanged(Pokemon pokemon) {
                        binding.pokemonRightHP.setText( String.valueOf( pokemon.getHp() ) );
                        reduceBar(progressBarRight, pokemon);
                    }
                });


        pbViewModel.getPokemonLowHp().observe(getViewLifecycleOwner(), new Observer<Pokemon>() {
            @Override
            public void onChanged(Pokemon pokemonLow) {
                if ( pokemonLow !=  null ) {
                    Snackbar snackbar = Snackbar.make(view, "El pokemon: " + pokemonLow.getNombre() + " tiene muy poca vida!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        pbViewModel.getIsNormalAttack().observe(getViewLifecycleOwner(), isNormal -> {
            if ( isNormal ) {
                HomeFragment.showText(view,
                        "Pokemon :" + Objects.requireNonNull(pbViewModel.getPokemonCurrentAttacker().getValue()).getNombre() +
                        " a hecho un ataque normal!");
            } else {
                HomeFragment.showText(view,
                        "Pokemon :" + Objects.requireNonNull(pbViewModel.getPokemonCurrentAttacker().getValue()).getNombre() +
                                " a hecho un ataque especial!");
            }
        });



        }

    public void reduceBar(ProgressBar pb, Pokemon p) {
        int porcentajeVida = (int) (100 - ( ( (double) (p.getMaxHp() - p.getHp()) / p.getMaxHp() ) * 100 ));
        if (porcentajeVida < 25) {
            pb.setProgressTintList(ColorStateList.valueOf(Color.RED));
        } else if (porcentajeVida < 50) {
            pb.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#ffa500")));
        }
        pb.setProgress(p.getHp());
    }

}