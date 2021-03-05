package com.agy3ya.foodrecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeActivity extends AppCompatActivity {

    private TextView title, ready_in, servings, healthy, instructions;
    private ImageView img, vegeterian;
    private JSONArray ingredientsArr;
    private List<Ingredient> ingredientsLst = new ArrayList<Ingredient>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        final Intent intent = getIntent();
        final String recipeId = Objects.requireNonNull(intent.getExtras()).getString("id");
        img = findViewById(R.id.recipe_img);
        title = findViewById(R.id.recipe_title);
        ready_in = findViewById(R.id.recipe_ready_in);
        servings = findViewById(R.id.recipe_servings);
        healthy = findViewById(R.id.recipe_healthy);
        vegeterian = findViewById(R.id.recipe_vegetarian);
        instructions = findViewById(R.id.recipe_instructions);
        getRecipeData(recipeId);


        recyclerView = findViewById(R.id.recipe_ingredients_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void getRecipeData(final String recipeId) {
        String URL = " https://api.spoonacular.com/recipes/" + recipeId + "/information?apiKey=f56c86cf89d843ca83cbb5c92a2c9dfd";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    try {
                        try {
                            Picasso.get().load((String) response.get("image")).into(img);
                        }
                        catch (Exception e){
                            img.setImageResource(R.drawable.ic_baseline_photo_24);
                        }
                        title.setText((String) response.get("title"));
                        ready_in.setText(Integer.toString((Integer) response.get("readyInMinutes")));
                        servings.setText(Integer.toString((Integer) response.get("servings")));
                        if ((boolean) response.get("veryHealthy")) {
                            healthy.setText("Healthy");
                        }
                        if ((boolean) response.get("vegetarian")) {
                            vegeterian.setImageResource(R.drawable.ic_veg);
                        }
                        try{
                            if(response.get("instructions").equals("")){
                                throw new Exception("No Instructions");
                            }
                            else
                                instructions.setText(Html.fromHtml((String) response.get("instructions")));
                        }
                        catch(Exception e){
                            String msg= "the recipe you were looking for not found, to view the original recipe click on the link below:" + "<a href="+response.get("spoonacularSourceUrl")+">"+response.get("spoonacularSourceUrl")+"</a>";
                            instructions.setMovementMethod(LinkMovementMethod.getInstance());
                            instructions.setText(Html.fromHtml(msg));
                        }
                        ingredientsArr = (JSONArray) response.get("extendedIngredients");
                        for (int i = 0; i < ingredientsArr.length(); i++) {
                            JSONObject jsonObject1;
                            jsonObject1 = ingredientsArr.getJSONObject(i);
                            ingredientsLst.add(new Ingredient(jsonObject1.optString("originalString"), jsonObject1.optString("image")));
                        }
                        RecyclerViewIngredientAdapter myAdapter = new RecyclerViewIngredientAdapter(getApplicationContext(), ingredientsLst);
                        recyclerView.setAdapter(myAdapter);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAlpha(1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.i("the res is error:", error.toString())
        );
        requestQueue.add(jsonObjectRequest);
    }

}
