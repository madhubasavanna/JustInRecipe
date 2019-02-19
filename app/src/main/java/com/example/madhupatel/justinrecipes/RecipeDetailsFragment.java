package com.example.madhupatel.justinrecipes;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeDetailsFragment extends Fragment implements RecipeDetailsAdapter.StepsItemClickListener,
        RecipeDetailsAdapter.IngredientsListClickListener,
        RecipeDetailsAdapter.UpdateWidgetClickListener {
    @BindView(R.id.recipe_details_recycler_view)
    RecyclerView recipeDetailsRecyclerView;
    Recipe recipeData;
    List<Step> steps;
    List<Ingredient> ingredients;
    IngredientsClickListener ingredientsClickListener;
    StepsClickListener stepsClickListener;
    AddWidgetClickListener addWidgetClickListener;


    public interface IngredientsClickListener {
        void onInredientsClick(Recipe recipe);
    }

    public interface StepsClickListener {
        void onStepsClick(Recipe recipe, int id);
    }

    public interface AddWidgetClickListener{
        void onAddWidgetClick();
    }

    public RecipeDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        recipeDetailsRecyclerView = rootView.findViewById(R.id.recipe_details_recycler_view);
        recipeDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recipeData = getArguments().getParcelable("recipeData");
        steps = recipeData.getSteps();
        ingredients = recipeData.getIngredients();
        recipeDetailsRecyclerView.setAdapter(new RecipeDetailsAdapter(steps, this,
                this, this));

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            ingredientsClickListener = (IngredientsClickListener) context;
            stepsClickListener = (StepsClickListener) context;
            addWidgetClickListener = (AddWidgetClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnIngridentClick or OnSteplsClick or OnAddWidgetClick");
        }
    }

    @Override
    public void onStepsClicked(int position, Step step) {
        stepsClickListener.onStepsClick(recipeData, position);
    }

    @Override
    public void onIngredientsClicked(int position) {
        ingredientsClickListener.onInredientsClick(recipeData);
    }

    @Override
    public void OnWidgetClick() {
        addWidgetClickListener.onAddWidgetClick();
    }
}
