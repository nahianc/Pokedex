package com.example.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context context;
    RequestOptions option;
    ArrayList<PokemonModel> pokemonModels;

    public RecyclerViewAdapter(Context context, ArrayList<PokemonModel> pokemonModels) {
        this.context = context;
        this.pokemonModels = pokemonModels;
        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_image).error(R.drawable.loading_image);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Giving the recycler view the "outline" to recycle
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_recycler, parent, false);
        return new RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        // Assigning data/values to recycled view elements
        holder.listPokeNum.setText(Integer.toString(pokemonModels.get(position).getPokeDexNum()));
        holder.listPokeName.setText(pokemonModels.get(position).getPokeName());
        Glide.with(context).load(pokemonModels.get(position).getPokeImg()).apply(option).into(holder.listPokeImg);
    }

    @Override
    public int getItemCount() {
        // The recycler view needs to know how many items are in the list
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
