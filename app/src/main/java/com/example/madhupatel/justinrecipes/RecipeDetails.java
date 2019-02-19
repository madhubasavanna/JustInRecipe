package com.example.madhupatel.justinrecipes;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.List;


public class RecipeDetails extends AppCompatActivity implements RecipeDetailsFragment.IngredientsClickListener,
        RecipeDetailsFragment.StepsClickListener, RecipeDetailsFragment.AddWidgetClickListener, StepsDetail.OnStepsDetailClickListener {
    Recipe recipe;
    static List<Ingredient> ingredients;
    static boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        //Here you get actionbar features if you
//        ActionBar actionBar = getSupportActionBar();
//        //actionBar.setHomeAsUpIndicator(R.drawable.icon_menu);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//
//        toolbar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int count = getSupportFragmentManager().getBackStackEntryCount();
//                if(count != 0){
//                    getSupportFragmentManager().popBackStack();
//                }
//            }
//        });
        if(savedInstanceState == null){
            mTwoPane = getIntent().getBooleanExtra("TwoPane", false);
            if(mTwoPane){
                recipe = getIntent().getParcelableExtra("recipe_data");
                ingredients = recipe.getIngredients();
                Bundle bundle = new Bundle();
                bundle.putParcelable("recipeData", recipe);
                RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
                recipeDetailsFragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.recipe_steps_fragment_tab,recipeDetailsFragment).commit();

                IngredientsListFragment ingredientsFragment = new IngredientsListFragment();
                Bundle ingredientsBundle = new Bundle();
                ingredientsBundle.putParcelable("recipeData", recipe);
                ingredientsFragment.setArguments(ingredientsBundle);
                fragmentManager.beginTransaction().replace(R.id.recipe_details_fragment_tab,ingredientsFragment).commit();
            }
            else {
                recipe = getIntent().getParcelableExtra("recipe_data");
                ingredients = recipe.getIngredients();
                Bundle bundle = new Bundle();
                bundle.putParcelable("recipeData", recipe);
                RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
                recipeDetailsFragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.recipe_details_container,recipeDetailsFragment).commit();
            }
        }else {
            mTwoPane = savedInstanceState.getBoolean("TwoPane");
            if(mTwoPane){
                recipe = savedInstanceState.getParcelable("recipeData");
                ingredients = recipe.getIngredients();
                Bundle bundle = new Bundle();
                bundle.putParcelable("recipeData", recipe);
                RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
                recipeDetailsFragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.recipe_steps_fragment_tab,recipeDetailsFragment).commit();
            }else {
                recipe = savedInstanceState.getParcelable("recipeData");
                ingredients = recipe.getIngredients();
                Bundle bundle = new Bundle();
                bundle.putParcelable("recipeData", recipe);
                RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
                recipeDetailsFragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.recipe_details_container,recipeDetailsFragment).commit();
            }
        }
    }

    @Override
    public void onInredientsClick(Recipe recipe) {
        if(mTwoPane){
            IngredientsListFragment ingredientsFragment = new IngredientsListFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("recipeData", recipe);
            ingredientsFragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.recipe_details_fragment_tab,ingredientsFragment).commit();
        }else {
            IngredientsListFragment ingredientsFragment = new IngredientsListFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("recipeData", recipe);
            ingredientsFragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.recipe_details_container,ingredientsFragment).addToBackStack("ingrdientFragment").commit();
        }
    }

    @Override
    public void onStepsClick(Recipe recipe, int position) {
        if(mTwoPane){
            StepsDetail stepsDetail = new StepsDetail();
            Bundle bundle = new Bundle();
            bundle.putParcelable("recipeData", recipe);
            bundle.putInt("position", position);
            stepsDetail.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.recipe_details_fragment_tab, stepsDetail).commit();
        }else {
            Intent intent = new Intent(this, StepsDetailsActivity.class);
            intent.putExtra("recipeData", recipe);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    }

    @Override
    public void onStepsDetailClick(int p) {
        int position = p;

        StepsDetail stepsDetail = new StepsDetail();
        Bundle bundle = new Bundle();
        bundle.putParcelable("recipeData", recipe);
        bundle.putInt("position", p);
        stepsDetail.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.recipe_details_fragment_tab, stepsDetail).commit();
    }

    @Override
    public void onAddWidgetClick() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(RecipeDetails.this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(RecipeDetails.this, RecipeListWidget.class));
        //Trigger data update to handle the ListView widgets and force a data refresh
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_ingredients_list);
        RecipeListWidget.updateIngredientsList(getApplicationContext(),appWidgetManager,
                recipe,appWidgetIds);
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        if(count == 0){
            super.onBackPressed();
        }else {
            getSupportFragmentManager().popBackStack();
        }
    }

    public static List<Ingredient> getIngredientsData(){
        return ingredients;
    }

    @Override
    protected void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelable("recipeData", recipe);
        currentState.putBoolean("TwoPane", mTwoPane);
    }
}
