package com.example.pokedex;

public class PokemonModel {
    int pokeDexNum;
    String pokeName, pokeDescription, pokeImg;
    String[] pokeType;
    String[] pokeAbility;
    String[] pokeEvolution;
    int[] pokeStats;

    public PokemonModel(String pokeName, int pokeDexNum, String pokeDescription, String pokeImg,
                        String[] pokeType, String[] pokeAbility, String[] pokeEvolution) {
        this.pokeName = pokeName;
        this.pokeDexNum = pokeDexNum;
        this.pokeDescription = pokeDescription;
        this.pokeImg = pokeImg;
        this.pokeType = pokeType;
        this.pokeAbility = pokeAbility;
        this.pokeEvolution = pokeEvolution;
    }

    public PokemonModel(String pokeName, int pokeDexNum, String pokeImg) {
        this.pokeName = pokeName;
        this.pokeDexNum = pokeDexNum;
        this.pokeImg = pokeImg;
    }

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

}