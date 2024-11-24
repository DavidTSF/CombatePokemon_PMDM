package dev.davveg.combatepokemon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

import dev.davveg.combatepokemon.databinding.FragmentHomeBinding;
import dev.davveg.combatepokemon.pokemon.Pokemon;
import dev.davveg.combatepokemon.viewmodel.PokemonBattleViewModel;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    NavController navController;

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
        navController = Navigation.findNavController(view);
        Button buttonStart = binding.buttonStart;

        buttonStart.setOnClickListener(buttonView -> {
            Pokemon pokemonLeft = new Pokemon();
            Pokemon pokemonRight = new Pokemon();
            String attWithError = "";
            PokemonBattleViewModel pbViewModel = new ViewModelProvider(requireActivity()).get(PokemonBattleViewModel.class);
            boolean valido = true;

            try {
                pokemonLeft.setNombre( binding.homePokemonLName.getText().toString() );

                int pokemonLeftHP = Integer.parseInt(binding.homePokemonLHP.getText().toString());
                if ( isAValidValue(pokemonLeftHP) ) {
                    pokemonLeft.setHp(pokemonLeftHP);
                    pokemonLeft.setMaxHp(pokemonLeftHP);
                } else {
                    attWithError += "HP, ";
                    valido = false;
                }
                int pokemonLeftAttack = Integer.parseInt(binding.homePokemonLAttack.getText().toString());
                if ( isAValidValue(pokemonLeftAttack) ) {
                    pokemonLeft.setAtaque(pokemonLeftAttack);
                }else {
                    attWithError += "Ataque, ";
                    valido = false;
                }
                int pokemonLeftDefense = Integer.parseInt( binding.homePokemonLDefense.getText().toString());
                if ( isAValidValue(pokemonLeftDefense)  ) {
                    pokemonLeft.setDefensa(pokemonLeftDefense);
                }else {
                    attWithError += "Defensa, ";
                    valido = false;
                }
                int pokemonLeftAttackEx = Integer.parseInt( binding.homePokemonLAttackEx.getText().toString());
                if ( isAValidValue(pokemonLeftAttackEx) ) {
                    pokemonLeft.setAtaqueEspecial(pokemonLeftAttackEx);
                }else {
                    attWithError += "Ataque especial, ";
                    valido = false;
                }
                int pokemonLeftDefenseEx = Integer.parseInt( binding.homePokemonLDefenseEx.getText().toString());
                if ( isAValidValue(pokemonLeftDefenseEx) ) {
                    pokemonLeft.setDefensaEspecial(pokemonLeftDefenseEx);
                }else {
                    attWithError += "Defensa especial, ";
                    valido = false;
                }

                if ( !valido ) {
                    showErrorValues(view, attWithError, "izquierda");
                    return;
                }
                attWithError="";

                pokemonRight.setNombre( binding.homePokemonRName.getText().toString() );

                int pokemonRightHP = Integer.parseInt(binding.homePokemonRHP.getText().toString());
                if ( isAValidValue(pokemonRightHP) ) {
                    pokemonRight.setHp(pokemonRightHP);
                    pokemonRight.setMaxHp(pokemonRightHP);
                } else {
                    attWithError += "HP, ";
                    valido = false;
                }
                int pokemonRightAttack = Integer.parseInt(binding.homePokemonRAttack.getText().toString());
                if ( isAValidValue(pokemonRightAttack) ) {
                    pokemonRight.setAtaque(pokemonRightAttack);
                }else {
                    attWithError += "Ataque, ";
                    valido = false;
                }
                int pokemonRightDefense = Integer.parseInt( binding.homePokemonRDefense.getText().toString());
                if ( isAValidValue(pokemonRightDefense)  ) {
                    pokemonRight.setDefensa(pokemonRightDefense);
                }else {
                    attWithError += "Defensa, ";
                    valido = false;
                }
                int pokemonRightAttackEx = Integer.parseInt( binding.homePokemonRAttackEx.getText().toString());
                if ( isAValidValue(pokemonRightAttackEx) ) {
                    pokemonRight.setAtaqueEspecial(pokemonRightAttackEx);
                }else {
                    attWithError += "Ataque especial, ";
                    valido = false;
                }
                int pokemonRightDefenseEx = Integer.parseInt( binding.homePokemonRDefenseEx.getText().toString());
                if ( isAValidValue(pokemonRightDefenseEx) ) {
                    pokemonRight.setDefensaEspecial(pokemonRightDefenseEx);
                }else {
                    attWithError += "Defensa especial, ";
                    valido = false;
                }

                if ( !valido ) {
                    showErrorValues(view, attWithError, "derecha");
                    return;
                }

                pbViewModel.getPokemon_left().postValue(pokemonLeft);
                pbViewModel.getPokemon_right().postValue(pokemonRight);

                navController.navigate(R.id.action_homeFragment_to_pokemonBattle);

            } catch (Exception e ) {
                showErrorText(view, "Faltan valores por introducir/se ha introducido texto en campos numericos");
                e.printStackTrace();
            }

        });
    }

    public boolean isAValidValue(int n) {
        return n >= 0 && n <= 999;
    }

    public void showErrorValues(View view, String nombreValor, String lado) {
        Snackbar snackbar = Snackbar.make( view, "El valor o valores: " + nombreValor + "del pokemon " + lado +" tiene que estar entre 0-999", Snackbar.LENGTH_LONG );
        snackbar.show();
    }

    public void showErrorText(View view, String text) {
        Snackbar snackbar = Snackbar.make( view, text, Snackbar.LENGTH_LONG );
        snackbar.show();
    }

}