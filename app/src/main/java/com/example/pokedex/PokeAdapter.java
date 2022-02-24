package com.example.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PokeAdapter extends RecyclerView.Adapter<PokeAdapter.MyViewHolder> {

    Context context;
    ArrayList<PokemonModel> pokemonModels;

    public PokeAdapter(Context context, ArrayList<PokemonModel> pokemonModels) {
        this.context = context;
        this.pokemonModels = pokemonModels;
    }

    @NonNull
    @Override
    public PokeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Giving the recycler view the "outline" to recycle
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_recycler, parent, false);
        return new PokeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokeAdapter.MyViewHolder holder, int position) {
        // Assigning data/values to recycled view elements
        holder.listPokeNum.setText(pokemonModels.get(position).getPokeDexNum());
        holder.listPokeName.setText(pokemonModels.get(position).getPokeName());
        Picasso.get().load(pokemonModels.get(position).getPokeImg()).into(holder.listPokeImg);
    }

    @Override
    public int getItemCount() {
        // The recycler view wants to know how many items are in the list
        return pokemonModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Grabbing views from the recycler layout file and assigning them to variables
        TextView listPokeNum, listPokeName;
        ImageView listPokeImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            listPokeNum = itemView.findViewById(R.id.listPokeNum);
            listPokeName = itemView.findViewById(R.id.listPokeName);
            listPokeImg = itemView.findViewById(R.id.listPokeImg);
        }
    }

}
