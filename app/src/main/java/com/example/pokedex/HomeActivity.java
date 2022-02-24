package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ArrayList<PokemonModel> pokemonModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        // Create and populate list of Pokemon objects *before* creating adapter
        setUpPokemonModels();
        PokeAdapter adapter = new PokeAdapter(this, pokemonModels);
        // After list and adapter have been setup, we can send the adapter to the recycler view
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpPokemonModels() {
        // TODO
        // https://raw.githubusercontent.com/Purukitto/pokemon-data.json/master/pokedex.json
        pokemonModels.add(new PokemonModel("1", "Bulbasaur", "https://raw.githubusercontent.com/Purukitto/pokemon-data.json/master/images/pokedex/thumbnails/001.png"));
        pokemonModels.add(new PokemonModel("2", "Ivysaur", "https://raw.githubusercontent.com/Purukitto/pokemon-data.json/master/images/pokedex/thumbnails/002.png"));
        pokemonModels.add(new PokemonModel("3", "Venasaur", "https://raw.githubusercontent.com/Purukitto/pokemon-data.json/master/images/pokedex/thumbnails/003.png"));
        pokemonModels.add(new PokemonModel("4", "Charmander", "https://raw.githubusercontent.com/Purukitto/pokemon-data.json/master/images/pokedex/thumbnails/004.png"));
        pokemonModels.add(new PokemonModel("5", "Charmeleon", "https://raw.githubusercontent.com/Purukitto/pokemon-data.json/master/images/pokedex/thumbnails/005.png"));
        pokemonModels.add(new PokemonModel("6", "Charizard", "https://raw.githubusercontent.com/Purukitto/pokemon-data.json/master/images/pokedex/thumbnails/006.png"));
        pokemonModels.add(new PokemonModel("7", "Squirtle", "https://raw.githubusercontent.com/Purukitto/pokemon-data.json/master/images/pokedex/thumbnails/007.png"));
        pokemonModels.add(new PokemonModel("8", "Wartortle", "https://raw.githubusercontent.com/Purukitto/pokemon-data.json/master/images/pokedex/thumbnails/008.png"));

    }
}