package com.nahianc.pokedex;

import android.os.Parcel;
import android.os.Parcelable;

public class PokemonModel implements Parcelable {
    private int id;
    private String name, pokeDescription, imgPath;
    private String[] types, abilities, evolution;
    private int[] stats;

    public PokemonModel(String name, int id, String pokeDescription, String imgPath,
                        String[] types, String[] abilities, String[] evolution, int[] stats) {
        this.name = name;
        this.id = id;
        this.pokeDescription = pokeDescription;
        this.imgPath = imgPath;
        this.types = types;
        this.abilities = abilities;
        this.evolution = evolution;
        this.stats = stats;
    }

    public PokemonModel(String name, int id, String imgPath) {
        this.name = name;
        this.id = id;
        this.imgPath = imgPath;
    }

    protected PokemonModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        pokeDescription = in.readString();
        imgPath = in.readString();
        types = in.createStringArray();
        abilities = in.createStringArray();
        evolution = in.createStringArray();
        stats = in.createIntArray();
    }

    public static final Creator<PokemonModel> CREATOR = new Creator<PokemonModel>() {
        @Override
        public PokemonModel createFromParcel(Parcel in) {
            return new PokemonModel(in);
        }

        @Override
        public PokemonModel[] newArray(int size) {
            return new PokemonModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getPokeDescription() {
        return pokeDescription;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String[] getTypes() {
        return types;
    }

    public String[] getAbilities() {
        return abilities;
    }

    public String[] getEvolution() {
        return evolution;
    }

    public int[] getStats() {
        return stats;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(pokeDescription);
        parcel.writeString(imgPath);
        parcel.writeStringArray(types);
        parcel.writeStringArray(abilities);
        parcel.writeStringArray(evolution);
        parcel.writeIntArray(stats);
    }
}