package com.agy3ya.foodrecipe;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeFragment extends Fragment implements View.OnClickListener  {
    private List<Recipe> recipeList = new ArrayList<>();
    private List<Recipe> searchRecipe;
    private JSONArray jsonArray;
    private ImageButton searchButton;
    private TextView searchTextView, emptyTextView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       final View rootView = inflater.inflate(R.layout.fragment_recipe,container,false);
        Toolbar mToolBar = rootView.findViewById(R.id.toolbar);
       ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(mToolBar);
        progressBar = rootView.findViewById(R.id.progressbar2);
        emptyTextView = rootView.findViewById(R.id.empty_view2);
        recyclerView = rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        getRandomRecipes();
        searchTextView = rootView.findViewById(R.id.home_search_et);
        searchButton = rootView.findViewById(R.id.home_search_btn);
        searchButton.setOnClickListener(this);
        searchTextView.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                if(!v.getText().toString().equals("")){
                    emptyTextView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setAlpha(0);
                    searchRecipe(v.getText().toString());
                }else{
                    Toast.makeText(getContext(),"Type anything...",Toast.LENGTH_LONG).show();

                }
            }
            return  false;

        });
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        return rootView;
    }

    private void searchRecipe(String search) {
        searchRecipe = new ArrayList<Recipe>();
        // String URL="https://api.spoonacular.com/recipes/search?query=" + search + "&number=30&instructionsRequired=true&apiKey=f56c86cf89d843ca83cbb5c92a2c9dfd";
        String URL="https://api.spoonacular.com/recipes/search?query=" + search + "&instructionsRequired=true&apiKey=f56c86cf89d843ca83cbb5c92a2c9dfd";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    try {
                        jsonArray = (JSONArray) response.get("results");
                        Log.i("the search res is:", String.valueOf(jsonArray));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1;
                            jsonObject1 = jsonArray.getJSONObject(i);
                            searchRecipe.add(new Recipe(jsonObject1.optString("id"),jsonObject1.optString("title"), "https://spoonacular.com/recipeImages/" + jsonObject1.optString("image"), Integer.parseInt(jsonObject1.optString("servings")), Integer.parseInt(jsonObject1.optString("readyInMinutes"))));
                        }
                        progressBar.setVisibility(View.GONE);
                        if(searchRecipe.isEmpty()){
                            recyclerView.setAlpha(0);
                            emptyTextView.setVisibility(View.VISIBLE);
                        }
                        else{
                            emptyTextView.setVisibility(View.GONE);
                            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), searchRecipe);
                            recyclerView.setAdapter(myAdapter);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAlpha(1);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.i("the res is error:", error.toString())
        );
        requestQueue.add(jsonObjectRequest);

    }

    private void getRandomRecipes() {
        String URL = " https://api.spoonacular.com/recipes/random?number=30&instructionsRequired=true&apiKey=f56c86cf89d843ca83cbb5c92a2c9dfd ";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    try {
                        jsonArray = (JSONArray) response.get("recipes");
                        Log.i("the res is:", String.valueOf(jsonArray));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1;
                            jsonObject1 = jsonArray.getJSONObject(i);
                            recipeList.add(new Recipe(jsonObject1.optString("id"),jsonObject1.optString("title"), jsonObject1.optString("image"), Integer.parseInt(jsonObject1.optString("servings")), Integer.parseInt(jsonObject1.optString("readyInMinutes"))));
                        }
                        progressBar.setVisibility(View.GONE);
                        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), recipeList);
                        recyclerView.setAdapter(myAdapter);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.i("the res is error:", error.toString());
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAlpha(0);
                    emptyTextView.setVisibility(View.VISIBLE);
                }
        );
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onClick(View v) {
        if(v==searchButton){
            try {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {
            }
            if(!searchTextView.getText().toString().equals("")) {
                progressBar.setVisibility(View.VISIBLE);
                recyclerView.setAlpha(0);
                searchRecipe(searchTextView.getText().toString());
            }
            else
                Toast.makeText(getContext(), "Type something...", Toast.LENGTH_LONG).show();
        }

    }
}
