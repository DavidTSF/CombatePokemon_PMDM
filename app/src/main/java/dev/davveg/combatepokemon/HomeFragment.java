package dev.davveg.combatepokemon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import dev.davveg.combatepokemon.databinding.FragmentHomeBinding;
import dev.davveg.combatepokemon.databinding.FragmentPokemonBattleBinding;
import dev.davveg.combatepokemon.pokemon.Pokemon;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentHomeBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        Button buttonStart = binding.buttonStart;
        buttonStart.setOnClickListener(buttonView -> {
            boolean valido = true;
            Toast toast;
            Pokemon pokemonLeft = new Pokemon();

            pokemonLeft.setNombre( binding.homePokemonLName.getText().toString() );

            int pokemonLeftHP = Integer.parseInt(binding.homePokemonLHP.getText().toString());
            if ( isAValidValue(pokemonLeftHP) ) {
                pokemonLeft.setHp(pokemonLeftHP);
            } else {
                valido = false;
                toast = Toast.makeText(this, "a");
                toast.show();

            }
            int pokemonLeftAttack = Integer.parseInt(binding.homePokemonLAttack.getText().toString());
            if ( isAValidValue(pokemonLeftAttack) ) {
                pokemonLeft.setAtaque(pokemonLeftAttack);
            }else {
                valido = false;
            }
            int pokemonLeftDefense = Integer.parseInt( binding.homePokemonLDefense.getText().toString());
            if ( isAValidValue(pokemonLeftDefense) ) {
                pokemonLeft.setDefensa(pokemonLeftDefense);
            }else {
                valido = false;
            }
            int pokemonLeftAttackEx = Integer.parseInt( binding.homePokemonLAttackEx.getText().toString());
            if ( isAValidValue(pokemonLeftAttackEx) ) {
                pokemonLeft.setAtaqueEspecial(pokemonLeftAttackEx);
            }else {
                valido = false;
            }
            int pokemonLeftDefenseEx = Integer.parseInt( binding.homePokemonLDefenseEx.getText().toString());
            if ( isAValidValue(pokemonLeftDefenseEx) ) {
                pokemonLeft.setDefensaEspecial(pokemonLeftDefenseEx);
            }else {
                valido = false;
            }










            Bundle result = n

                        ew Bundle();
            result.putSerializable("pokemon1", pokemonLeft);

            getParentFragmentManager().setFragmentResult("requestKey", result);

        });
    }

    public boolean isAValidValue(int n) {
        return n > 0 && n <= 999;
    }

}