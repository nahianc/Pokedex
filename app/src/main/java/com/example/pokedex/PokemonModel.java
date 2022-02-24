package com.example.pokedex;

public class PokemonModel {
    String pokeName, pokeDexNum, pokeDescription, pokeImg;
    String[] pokeType = new String[2];
    String[] pokeAbility = new String[3];
    String[] pokeEvolution = new String[2];

    public PokemonModel(String pokeName, String pokeDexNum, String pokeDescription, String pokeImg,
                        String[] pokeType, String[] pokeAbility, String[] pokeEvolution) {
        this.pokeName = pokeName;
        this.pokeDexNum = pokeDexNum;
        this.pokeDescription = pokeDescription;
        this.pokeImg = pokeImg;
        this.pokeType = pokeType;
        this.pokeAbility = pokeAbility;
        this.pokeEvolution = pokeEvolution;
    }

    public PokemonModel(String pokeName, String pokeDexNum, String pokeImg) {
        this.pokeName = pokeName;
        this.pokeDexNum = pokeDexNum;
        this.pokeImg = pokeImg;
    }

    public String getPokeName() {
        return pokeName;
    }

    public String getPokeDexNum() {
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
}