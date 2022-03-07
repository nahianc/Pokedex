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
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private final OnItemClickListener mOnItemClickListener;
    Context context;
    RequestOptions option;
    ArrayList<PokemonModel> pokemonModels;

    public RecyclerViewAdapter(Context context, ArrayList<PokemonModel> pokemonModels, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.pokemonModels = pokemonModels;
        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_image).error(R.drawable.loading_image);
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Giving the recycler view the "outline" to recycle
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_recycler, parent, false);
        return new RecyclerViewAdapter.MyViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        // Assigning data/values to recycled view elements
        holder.listPokeNum.setText( "#" + String.format(Locale.US, "%03d", pokemonModels.get(position).getPokeDexNum()) );
        holder.listPokeName.setText(pokemonModels.get(position).getPokeName());
        holder.listPokeType1.setText((pokemonModels.get(position).getPokeType())[0]);
        holder.listPokeType1.setPadding(15, 0, 15, 0);
        // Apply padding only if model contains second type
        if ( pokemonModels.get(position).getPokeType()[1].equals("") ) {
            holder.listPokeType2.setText("");
            holder.listPokeType2.setPadding(0, 0, 0, 0);
        } else {
            holder.listPokeType2.setText((pokemonModels.get(position).getPokeType())[1]);
            holder.listPokeType2.setPadding(15, 0, 15, 0);
        }
        Glide.with(context)
                .load(pokemonModels.get(position).getPokeImg()).apply(option)
                .into(holder.listPokeImg);
    }

    @Override
    public int getItemCount() {
        return pokemonModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Grabbing views from the recycler layout file and assigning them to variables
        TextView listPokeNum, listPokeName, listPokeType1, listPokeType2;
        ImageView listPokeImg;
        OnItemClickListener onItemClickListener;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            listPokeNum = itemView.findViewById(R.id.listPokeNum);
            listPokeName = itemView.findViewById(R.id.listPokeName);
            listPokeType1 = itemView.findViewById(R.id.pokeType1Field);
            listPokeType2 = itemView.findViewById(R.id.pokeType2Field);
            listPokeImg = itemView.findViewById(R.id.listPokeImg);

            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View View) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


}
