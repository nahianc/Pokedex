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

public class HomeActivity extends AppCompatActivity {
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
        /*
        TODO:
        Collect Pokemon data from JSON or API and populate pokemonModels list
         */
        JSONrequest();
    }

    private void JSONrequest() {
        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObj  = null ;

                for (int i = 0 ; i < response.length(); i++ ) {
                    try {
                        jsonObj = response.getJSONObject(i) ;

                        // Parse PokeDex Number
                        int pokeDexNum = jsonObj.getInt("id");
                        // Parse Pokemon Name
                        JSONObject pokeNameJSONObj = jsonObj.getJSONObject("name");
                        String pokeName = pokeNameJSONObj.getString("english");
                        // Parse Pokemon Image
                        String pokeImg = jsonObj.getString("thumbnail");

                        System.out.println(pokeDexNum + " " + pokeName + " " + pokeImg);
                        pokemonModels.add(new PokemonModel(pokeName, pokeDexNum, pokeImg));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                feedToRecyclerView(pokemonModels);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request) ;
    }

    private void feedToRecyclerView(ArrayList<PokemonModel> pokemonModels) {
        // After list and adapter have been setup, we can send the adapter to the recycler view
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, pokemonModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

}