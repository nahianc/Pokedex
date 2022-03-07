package com.example.pokedex;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Locale;

public class DetailedActivity extends AppCompatActivity {

    PokemonModel pokemon;
    RequestOptions option;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        if(getIntent().hasExtra("selected_pokemon")) {
            pokemon = getIntent().getParcelableExtra("selected_pokemon");
        }

        TextView pokeNum = findViewById(R.id.detailedPokeNum);
        TextView pokeName = findViewById(R.id.detailedPokeName);
        TextView pokeType1 = findViewById(R.id.detailedPokeType1);
        TextView pokeType2 = findViewById(R.id.detailedPokeType2);
        ImageView pokeImg = findViewById(R.id.detailedPokeImg);
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> onBackPressed());

        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_image).error(R.drawable.loading_image);

        pokeNum.setText("#" + String.format(Locale.US, "%03d", pokemon.getPokeDexNum()));
        pokeName.setText(pokemon.getPokeName());
        pokeType1.setText(pokemon.getPokeType()[0]);
        pokeType1.setPadding(15, 0, 15, 0);
        if(pokemon.getPokeType()[1].equals("") ) {
            pokeType2.setText("");
            pokeType2.setPadding(0, 0, 0, 0);
        } else {
            pokeType2.setText(pokemon.getPokeType()[1]);
            pokeType2.setPadding(15, 0, 15, 0);
        }
        Glide.with(this)
                .load(pokemon.getPokeImg()).apply(option)
                .into(pokeImg);


    }
}
