package com.example.madhupatel.justinrecipes;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private boolean mTwoPane;

    // The Idling Resource which will be null in production.
    @Nullable
    private SimpleIdelingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdelingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdelingResource();
        }
        return mIdlingResource;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the IdlingResource instance
        getIdlingResource();
        if (savedInstanceState == null) {

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (findViewById(R.id.recipe_list_fragment_tab) != null) {
            mTwoPane = true;
            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipeListFragment recipeListFragment = new RecipeListFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean("TwoPane",true);
            recipeListFragment.setArguments(bundle);
            recipeListFragment.setContext(this);
            fragmentManager.beginTransaction().add(R.id.recipe_list_fragment_tab,recipeListFragment).commit();
        } else {
            mTwoPane = false;
            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipeListFragment recipeListFragment = new RecipeListFragment();
            //Bundle bundle = new Bundle();
            //setRecipeList();
            //bundle.putParcelableArray("recipeList", recipeList);
            //recipeListFragment.setArguments(bundle);
            Bundle bundle = new Bundle();
            bundle.putBoolean("TwoPane",false);
            recipeListFragment.setArguments(bundle);
            recipeListFragment.setContext(this);
            fragmentManager.beginTransaction().add(R.id.fragment_container, recipeListFragment).commit();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
