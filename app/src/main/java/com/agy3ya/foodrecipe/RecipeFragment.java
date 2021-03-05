package com.agy3ya.foodrecipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeFragment extends Fragment implements View.OnClickListener  {
    private List<Recipe> recipeList = new ArrayList<>();
    private List<Recipe> search Recipe;
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

    private void getRandomRecipes() {
    }

    @Override
    public void onClick(View v) {

    }
}
