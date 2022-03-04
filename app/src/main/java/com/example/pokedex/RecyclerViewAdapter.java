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

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    RequestOptions option;
    ArrayList<PokemonModel> pokemonModels;

    public RecyclerViewAdapter(Context context, ArrayList<PokemonModel> pokemonModels, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.pokemonModels = pokemonModels;
        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_image).error(R.drawable.loading_image);
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Giving the recycler view the "outline" to recycle
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_recycler, parent, false);
        return new RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        // Assigning data/values to recycled view elements
        holder.listPokeNum.setText( "#" + String.format("%03d", pokemonModels.get(position).getPokeDexNum()) );
        holder.listPokeName.setText(pokemonModels.get(position).getPokeName());
        holder.listPokeType1.setText((pokemonModels.get(position).getPokeType())[0]);
        holder.listPokeType1.setPadding(15, 0, 15, 0);
        holder.listPokeType2.setText((pokemonModels.get(position).getPokeType())[1]);
        // Apply padding only if model contains second type
        if ( holder.listPokeType2.getText() == "")
            holder.listPokeType2.setPadding(0, 0, 0, 0);
        else
            holder.listPokeType2.setPadding(15, 0, 15, 0);
        Glide.with(context).load(pokemonModels.get(position).getPokeImg()).apply(option).into(holder.listPokeImg);
    }

    @Override
    public int getItemCount() {
        // The recycler view needs to know how many items are in the list
        return pokemonModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Grabbing views from the recycler layout file and assigning them to variables
        TextView listPokeNum, listPokeName, listPokeType1, listPokeType2;
        ImageView listPokeImg;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            listPokeNum = itemView.findViewById(R.id.listPokeNum);
            listPokeName = itemView.findViewById(R.id.listPokeName);
            listPokeType1 = itemView.findViewById(R.id.pokeType1Field);
            listPokeType2 = itemView.findViewById(R.id.pokeType2Field);
            listPokeImg = itemView.findViewById(R.id.listPokeImg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }

}
