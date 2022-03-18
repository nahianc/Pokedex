package com.example.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private ItemClickListener listener;
    Context context;
    RequestOptions option;
    ArrayList<PokemonModel> pokemonModels;

    public RecyclerViewAdapter(Context context, ArrayList<PokemonModel> pokemonModels, ItemClickListener listener) {
        this.context = context;
        this.pokemonModels = pokemonModels;
        // Request option for Glide
        option = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.loading_image);
        this.listener = listener;
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
        holder.background.setCardBackgroundColor( getStringIdentifier(context, pokemonModels.get(position).getPokeType()[0]) );
        holder.listPokeNum.setText( "#" + String.format(Locale.US, "%03d", pokemonModels.get(position).getPokeDexNum()) );
        holder.listPokeName.setText(pokemonModels.get(position).getPokeName());

        holder.listPokeType1.setText((pokemonModels.get(position).getPokeType())[0]);
        setPaddingAndColor( holder.listPokeType1, holder.type1card, pokemonModels.get(position).getPokeType()[0] );

        holder.listPokeType2.setText((pokemonModels.get(position).getPokeType())[1]);
        setPaddingAndColor( holder.listPokeType2, holder.type2card, pokemonModels.get(position).getPokeType()[1] );

        Glide.with(context)
                .load(pokemonModels.get(position).getPokeImg())
                .apply(option)
                .into(holder.listPokeImg);

        holder.itemView.findViewById(R.id.listItemContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(pokemonModels.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return pokemonModels.size();
    }

    public void filterList(ArrayList<PokemonModel> filteredList) {
        pokemonModels = filteredList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Grabbing views from the recycler layout file and assigning them to variables
        TextView listPokeNum, listPokeName, listPokeType1, listPokeType2;
        ImageView listPokeImg;
        CardView type1card, type2card, background;
        ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.pokeItemCardView);
            listPokeNum = itemView.findViewById(R.id.listNum);
            listPokeName = itemView.findViewById(R.id.listName);
            listPokeType1 = itemView.findViewById(R.id.listType1Text);
            listPokeType2 = itemView.findViewById(R.id.listType2Text);
            listPokeImg = itemView.findViewById(R.id.listPokeImg);
            type1card = itemView.findViewById(R.id.listType1Card);
            type2card =itemView.findViewById(R.id.pokeType2Card);
        }

    }

    /*
    Set padding of text view based on text length
    Empty text field gets 0 padding so it appears invisible on screen
    Set color of pokemon type card container based on type
     */
    void setPaddingAndColor(TextView textView, CardView typeCard, String type) {
        if (textView.getText().length() > 0) {
            textView.setPadding(45, 0, 45, 0);
            if (!type.equals(""))
                typeCard.setCardBackgroundColor( getStringIdentifier(context, type) );
        } else if (textView.getText().length() > 4) {
            textView.setPadding(30, 0, 30, 0);
            if (!type.equals(""))
                typeCard.setCardBackgroundColor( getStringIdentifier(context, type) );
        } else
            textView.setPadding(0, 0, 0, 0);
    }

    /*
    Get color identifier from strings resource file
    */
    public static int getStringIdentifier(Context context, String name) {
        return context.getResources().getColor(context.getResources().getIdentifier(name, "color", context.getPackageName()));
    }

}
