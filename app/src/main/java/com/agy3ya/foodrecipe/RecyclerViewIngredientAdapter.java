package com.agy3ya.foodrecipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewIngredientAdapter extends RecyclerView.Adapter<RecyclerViewIngredientAdapter.MyViewHolder> {
    private Context mContext;
    private List<Ingredient> mData;
    public static List<String> ingredientsList;

    public RecyclerViewIngredientAdapter(Context mContext, List<Ingredient> mData) {
        this.mContext = mContext;
        this.mData = mData;
        ingredientsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerViewIngredientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_ingredient, parent, false);
        return new RecyclerViewIngredientAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewIngredientAdapter.MyViewHolder holder, int position) {
        holder.tv_ingredient_name.setText(mData.get(position).getName());
        Picasso.get().load(mData.get(position).getThumbnail()).into(holder.img_ingredient_thumbnail);
    }

    @Override
    public int getItemCount() {
       return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ingredient_name;
        ImageView img_ingredient_thumbnail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ingredient_name = itemView.findViewById(R.id.recipe_ingredient_name);
            img_ingredient_thumbnail = itemView.findViewById(R.id.recipe_ingredient_img);

        }
    }
}
