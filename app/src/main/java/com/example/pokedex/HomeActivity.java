package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements RecyclerViewInterface {
    final String JSON_URL = "https://raw.githubusercontent.com/Purukitto/pokemon-data.json/master/pokedex.json";
    JsonArrayRequest request ;
    RequestQueue requestQueue ;
    ArrayList<PokemonModel> pokemonModels;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pokemonModels = new ArrayList<>();
        recyclerView = findViewById(R.id.listRecyclerView);
        JSONrequest();
    }

    private void JSONrequest() {
        request = new JsonArrayRequest(JSON_URL, response -> {

            JSONObject pokeObj  = null;

            for (int i = 0 ; i < response.length(); i++) {
                try {
                    System.out.println(i);
                    pokeObj = response.getJSONObject(i) ;

                    // Parse PokeDex Number
                    int dexNum = pokeObj.getInt("id");

                    // Parse Name
                    JSONObject nameObj = pokeObj.getJSONObject("name");
                    String pokeName = nameObj.getString("english");

                    // Parse Image
                    String imgStr = pokeObj.getString("thumbnail");

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
                        pokeEvolution[1] = evolutionObj.getJSONArray("next").getString(0);
                    }

                    /*
                    These section was intended to parse base/stats,
                    however the JSON stops keeping track of base stats
                    from entry 810 and onwards.

                    JSONObject statsJSONObj = pokeObj.getJSONObject("base");
                    int[] pokeStats = { statsJSONObj.getInt("HP"),
                                        statsJSONObj.getInt("Attack"),
                                        statsJSONObj.getInt("Defense"),
                                        statsJSONObj.getInt("Sp. Attack"),
                                        statsJSONObj.getInt("Sp. Defense"),
                                        statsJSONObj.getInt("Speed")
                                      };
                    */

                    // Create model using parsed data and add to list
                    pokemonModels.add(new PokemonModel(pokeName, dexNum, descriptionStr, imgStr, pokeType, pokeAbility, pokeEvolution));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            // After data parsing and list population is complete, feed a populated list to recycler view
            feedToRecyclerView(pokemonModels);
        }, error -> {
        });

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request) ;
    }

    private void feedToRecyclerView(ArrayList<PokemonModel> pokemonModels) {
        // After list and adapter have been setup, we can send the adapter to the recycler view
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, pokemonModels, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {

    }
}