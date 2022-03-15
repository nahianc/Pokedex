package com.example.pokedex;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
        if(getIntent().hasExtra("next_evolution")) {
            PokemonModel next_evolution = getIntent().getParcelableExtra("next_evolution");
        }
        if(getIntent().hasExtra("prev_evolution")) {
            PokemonModel prev_evolution = getIntent().getParcelableExtra("prev_evolution");
        }

        View layout = findViewById(R.id.deatiledActivityLayout);
        layout.setBackgroundColor( getStringIdentifier(this, pokemon.getPokeType()[0]));

        TextView pokeNum = findViewById(R.id.detailedNum);
        TextView pokeName = findViewById(R.id.detailedName);
        TextView pokeType1 = findViewById(R.id.detailedType1Text);
        TextView pokeType2 = findViewById(R.id.detailedType2Text);
        TextView pokeDescription = findViewById(R.id.pokeDescription);
        CardView type1card = findViewById(R.id.detailedType1Card);
        CardView type2card = findViewById(R.id.detailedType2Card);

        ImageView pokeImg = findViewById(R.id.detailedPokeImg);

        ImageButton backButton = findViewById(R.id.imageButton);
        backButton.setOnClickListener(view -> onBackPressed());

        TextView HPnum = findViewById(R.id.hpNum);
        TextView attackNum = findViewById(R.id.attackNum);
        TextView defenseNum = findViewById(R.id.defenseNum);
        TextView spAttNum = findViewById(R.id.spAttackNum);
        TextView spDefNum = findViewById(R.id.spDefenseNum);
        TextView speedNum = findViewById(R.id.speedNum);
        ProgressBar hpBar = findViewById(R.id.hpProgressBar);
        ProgressBar attackBar = findViewById(R.id.attackProgressBar);
        ProgressBar defBar = findViewById(R.id.defProgressBar);
        ProgressBar spAttBar = findViewById(R.id.spAttProgressBar);
        ProgressBar spDefBar = findViewById(R.id.spDefProgressBar);
        ProgressBar speedBar = findViewById(R.id.speedProgressBar);

        TextView ability1 = findViewById(R.id.ability1);
        TextView ability2 = findViewById(R.id.ability2);
        TextView ability3 = findViewById(R.id.ability3);

        pokeNum.setText("#" + String.format(Locale.US, "%03d", pokemon.getPokeDexNum()));
        pokeName.setText(pokemon.getPokeName());

        pokeType1.setText(pokemon.getPokeType()[0]);
        setPadding(pokeType1);
        type1card.setCardBackgroundColor( getStringIdentifier(this, pokemon.getPokeType()[0]) );

        if(pokemon.getPokeType()[1].length() > 0) {
            pokeType2.setText(pokemon.getPokeType()[1]);
            setPadding(pokeType2);
            type2card.setCardBackgroundColor( getStringIdentifier(this, pokemon.getPokeType()[1]) );
        }

        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_image).error(R.drawable.loading_image);
        Glide.with(this)
                .load(pokemon.getPokeImg()).apply(option)
                .into(pokeImg);

        pokeDescription.setText(pokemon.getPokeDescription());

        ability1.setText(pokemon.getPokeAbility()[0]);
        ability2.setText(pokemon.getPokeAbility()[1]);
        ability3.setText(pokemon.getPokeAbility()[2]);

        hpBar.setProgress(pokemon.getPokeStats()[0]);
        HPnum.setText(Integer.toString(pokemon.getPokeStats()[0]));
        attackBar.setProgress(pokemon.getPokeStats()[1]);
        attackNum.setText(Integer.toString(pokemon.getPokeStats()[1]));
        defBar.setProgress(pokemon.getPokeStats()[2]);
        defenseNum.setText(Integer.toString(pokemon.getPokeStats()[2]));
        spAttBar.setProgress(pokemon.getPokeStats()[3]);
        spAttNum.setText(Integer.toString(pokemon.getPokeStats()[3]));
        spDefBar.setProgress(pokemon.getPokeStats()[4]);
        spDefNum.setText(Integer.toString(pokemon.getPokeStats()[4]));
        speedBar.setProgress(pokemon.getPokeStats()[5]);
        speedNum.setText(Integer.toString(pokemon.getPokeStats()[5]));


    }

    /*
    Set padding of text view based on text length
    Empty text field gets 0 padding so it appears invisible on screen
     */
    void setPadding(TextView textView) {
        if (textView.getText().length() > 0)
            textView.setPadding(45, 0, 45, 0);
        else if (textView.getText().length() > 4)
            textView.setPadding(30, 0, 30, 0);
        else
            textView.setPadding(0, 0, 0, 0);
    }

    /*
    Get color identifier from strings resource file
    */
    public static int getStringIdentifier(Context context, String name) {
        return context.getResources().getColor(context.getResources().getIdentifier(name, "color", context.getPackageName()));
    }

}
