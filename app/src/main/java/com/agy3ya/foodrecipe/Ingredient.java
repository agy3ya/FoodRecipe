package com.agy3ya.foodrecipe;

public class Ingredient {
    private String name;
    private String Thumbnail;

    public Ingredient(String name, String thumbnail) {
        this.name = name;
        Thumbnail = "https://spoonacular.com/cdn/ingredients 100x100" + thumbnail;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return Thumbnail;
    }
}
