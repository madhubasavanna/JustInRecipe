package com.example.madhupatel.justinrecipes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;

import butterknife.BindView;

public class RecipeListFragment extends Fragment implements RecipeListAdapter.RecipeListItemClickListener {
    private static final String URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    @BindView(R.id.recycler_view_home)
    RecyclerView recipesList;
    Context context;
    Boolean mTwoPane;

    public RecipeListFragment() {
    }

    public void setContext(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //ButterKnife.bind(this);
        View v = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        recipesList = v.findViewById(R.id.recycler_view_home);
        recipesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTwoPane = getArguments().getBoolean("TwoPane", true);

        StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("main", response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Recipe[] recipe = gson.fromJson(response, Recipe[].class);
                recipesList.setAdapter(new RecipeListAdapter(RecipeListFragment.this, Arrays.asList(recipe)));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

        return v;
    }

    @Override
    public void onRecipeListItemClick(Recipe recipe) {
        Intent intent = new Intent(getActivity(), RecipeDetails.class);
        intent.putExtra("recipe_data", recipe);
        intent.putExtra("TwoPane", mTwoPane);
        startActivity(intent);
    }
}
