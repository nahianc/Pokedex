package com.nahianc.pokedex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class ListActivity extends AppCompatActivity implements ItemClickListener {
    final String JSON_URL = "https://raw.githubusercontent.com/Purukitto/pokemon-data.json/master/pokedex.json";
    JsonArrayRequest request;
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    RecyclerViewAdapter adapter;
    ArrayList<PokemonModel> pokemonModels;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mAuth = FirebaseAuth.getInstance();

        pokemonModels = new ArrayList<>();

        recyclerView = findViewById(R.id.listRecyclerView);
        parseJSON();
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

        EditText searchBar = findViewById(R.id.searchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //Firebase SignOut
        mAuth.signOut();

        //Google SignOut
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient gsc = GoogleSignIn.getClient(this, gso);
        gsc.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });

        //Facebook SignOut
        LoginManager.getInstance().logOut();

    finish();
    }

    private void filter(String query) {
        ArrayList<PokemonModel> filteredList = new ArrayList<>();
        // If search bar is empty then repopulate with full list
        if (query == null || query.trim().isEmpty()) {
            adapter.filterList(pokemonModels);
            return;
        }
        // If user is searching by PokeDex number
        if ( Character.isDigit(query.charAt(0)) ) {
            for (PokemonModel item : pokemonModels) {
                if ( String.format(Locale.US, "%03d", item.getId()).contains(String.format(Locale.US, "%03d", Integer.parseInt(query)))) {
                    filteredList.add(item);
                }
            }
        // If user is searching by PokeDex name
        } else {
            for (PokemonModel item : pokemonModels) {
                if (item.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }
        adapter.filterList(filteredList);
    }

    private void feedToRecyclerView(ArrayList<PokemonModel> pokemonModels) {
        // After list and adapter have been setup, we can send the adapter to the recycler view
        adapter = new RecyclerViewAdapter(this, pokemonModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onItemClick(PokemonModel selectedPokemon) {
        Intent intent = new Intent(this, DetailedActivity.class);
        intent.putExtra("selected_pokemon", selectedPokemon);
        if (!selectedPokemon.getEvolution()[0].equals(""))
            intent.putExtra("prev_evolution", pokemonModels.get( Integer.parseInt( selectedPokemon.getEvolution()[0])-1));
        if (!selectedPokemon.getEvolution()[1].equals(""))
            intent.putExtra("next_evolution", pokemonModels.get( Integer.parseInt( selectedPokemon.getEvolution()[1])-1));
        startActivity(intent);
    }

    private void parseJSON() {
        request = new JsonArrayRequest(JSON_URL, response -> {

            JSONObject pokeObj  = null;

            for (int i = 0 ; i < response.length(); i++) {
                try {
                    pokeObj = response.getJSONObject(i) ;

                    // Parse PokeDex Number
                    int dexNum = pokeObj.getInt("id");

                    // Parse Name
                    JSONObject nameObj = pokeObj.getJSONObject("name");
                    String pokeName = nameObj.getString("english");

                    // Parse Image
                    String imgStr = pokeObj.getJSONObject("image").getString("thumbnail");

                    // Parse Description
                    String descriptionStr = pokeObj.getString("description");

                    // Parse types
                    JSONArray typeArr = pokeObj.getJSONArray("type");
                    String[] pokeType = {"", ""};
                    for (int j = 0; j < typeArr.length(); j++)
                        pokeType[j] = typeArr.getString(j);

                    // Parse abilities
                    JSONArray abilityArr = pokeObj.getJSONObject("profile").getJSONArray("ability");
                    String[] pokeAbility = {"", "", ""};
                    for (int j = 0; j < abilityArr .length(); j++)
                        pokeAbility[j] = abilityArr.getJSONArray(j).getString(0);

                    // Parse evolutions
                    JSONObject evolutionObj = pokeObj.getJSONObject("evolution");
                    String[] pokeEvolution = {"", ""};
                    if (evolutionObj.has("prev") && evolutionObj.getJSONArray("prev").length() > 0) {
                        pokeEvolution[0] = evolutionObj.getJSONArray("prev").getString(0);
                    }
                    if (evolutionObj.has("next") && evolutionObj.getJSONArray("next").length() > 0) {
                        pokeEvolution[1] = evolutionObj.getJSONArray("next").getJSONArray(0).getString(0);
                    }

                    /*
                    These section was intended to parse base/stats,
                    however the JSON stops keeping track of base stats
                    from entry 810 and onwards.
                    */
                    int[] pokeStats = {1, 1, 1, 1, 1, 1};
                    if( pokeObj.has("base") ) {
                        JSONObject statsJSONObj = pokeObj.optJSONObject("base");
                        pokeStats[0] = statsJSONObj.getInt("HP");
                        pokeStats[1] = statsJSONObj.getInt("Attack");
                        pokeStats[2] = statsJSONObj.getInt("Defense");
                        pokeStats[3] = statsJSONObj.getInt("Sp. Attack");
                        pokeStats[4] = statsJSONObj.getInt("Sp. Defense");
                        pokeStats[5] = statsJSONObj.getInt("Speed");
                    }

                    // Create model using parsed data and add to list
                    pokemonModels.add(new PokemonModel(pokeName, dexNum, descriptionStr, imgStr, pokeType, pokeAbility, pokeEvolution, pokeStats));
                    System.out.println(dexNum + " " + pokeName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            /*
            After data parsing and list population is complete,
            feed the populated list to recycler view
            */
            feedToRecyclerView(pokemonModels);
        }, error -> {
        });
    }
}