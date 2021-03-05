package com.agy3ya.foodrecipe;

public class Recipe {
    private int id;
    private String Title;
    private String Thumbnail;
    private int amountOfDishes;
    private int readyInMins;

    public Recipe(int id, String title, String thumbnail, int amountOfDishes, int readyInMins) {
        this.id = id;
        Title = title;
        Thumbnail = thumbnail;
        this.amountOfDishes = amountOfDishes;
        this.readyInMins = readyInMins;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return Title;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public int getAmountOfDishes() {
        return amountOfDishes;
    }

    public int getReadyInMins() {
        return readyInMins;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", Title='" + Title + '\'' +
                ", Thumbnail='" + Thumbnail + '\'' +
                ", amountOfDishes=" + amountOfDishes +
                ", readyInMins=" + readyInMins +
                '}';
    }
}
