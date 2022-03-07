package com.example.pokedex;

import android.os.Parcel;
import android.os.Parcelable;

public class PokemonModel implements Parcelable {
    int pokeDexNum;
    String pokeName, pokeDescription, pokeImg;
    String[] pokeType;
    String[] pokeAbility;
    String[] pokeEvolution;
    int[] pokeStats;

    public PokemonModel(String pokeName, int pokeDexNum, String pokeDescription, String pokeImg,
                        String[] pokeType, String[] pokeAbility, String[] pokeEvolution, int[] pokeStats) {
        this.pokeName = pokeName;
        this.pokeDexNum = pokeDexNum;
        this.pokeDescription = pokeDescription;
        this.pokeImg = pokeImg;
        this.pokeType = pokeType;
        this.pokeAbility = pokeAbility;
        this.pokeEvolution = pokeEvolution;
        this.pokeStats = pokeStats;
    }

    public PokemonModel(String pokeName, int pokeDexNum, String pokeImg) {
        this.pokeName = pokeName;
        this.pokeDexNum = pokeDexNum;
        this.pokeImg = pokeImg;
    }

    protected PokemonModel(Parcel in) {
        pokeDexNum = in.readInt();
        pokeName = in.readString();
        pokeDescription = in.readString();
        pokeImg = in.readString();
        pokeType = in.createStringArray();
        pokeAbility = in.createStringArray();
        pokeEvolution = in.createStringArray();
        pokeStats = in.createIntArray();
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

    public String getPokeName() {
        return pokeName;
    }

    public int getPokeDexNum() {
        return pokeDexNum;
    }

    public String getPokeDescription() {
        return pokeDescription;
    }

    public String getPokeImg() {
        return pokeImg;
    }

    public String[] getPokeType() {
        return pokeType;
    }

    public String[] getPokeAbility() {
        return pokeAbility;
    }

    public String[] getPokeEvolution() {
        return pokeEvolution;
    }

    public int[] getPokeStats() {
        return pokeStats;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(pokeDexNum);
        parcel.writeString(pokeName);
        parcel.writeString(pokeDescription);
        parcel.writeString(pokeImg);
        parcel.writeStringArray(pokeType);
        parcel.writeStringArray(pokeAbility);
        parcel.writeStringArray(pokeEvolution);
        parcel.writeIntArray(pokeStats);
    }
}