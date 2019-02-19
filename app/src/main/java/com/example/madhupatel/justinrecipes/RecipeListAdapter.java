package com.example.madhupatel.justinrecipes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewolder> {
    List<Recipe> recipes;
    private final RecipeListItemClickListener mOnClickListener;
    int position;

    public interface RecipeListItemClickListener{
        void onRecipeListItemClick(Recipe recipe);
    }

    public RecipeListAdapter(RecipeListItemClickListener listItemClickListener, List<Recipe> recipes){
        this.mOnClickListener = listItemClickListener;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.recipe_card_view, viewGroup,false);
        return new RecipeViewolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewolder recipeViewolder, int i) {
        position = i;
        recipeViewolder.bind(i);

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeViewolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.recipe_image)
        ImageView foodImage;
        @BindView(R.id.recipe_title)
        TextView foodName;
        String title;
        String imageUrl;
        Recipe recipe;

        public RecipeViewolder(@NonNull View itemView) {
            super(itemView);
            //ButterKnife.bind(itemView);
            foodName = itemView.findViewById(R.id.recipe_title);
            foodImage = itemView.findViewById(R.id.recipe_image);
            itemView.setOnClickListener(this);
        }

        public void bind(int position){
            recipe = recipes.get(position);
            title = recipe.getName();
            foodName.setText(title);
            imageUrl = recipe.getImage();

            if(imageUrl != "" && imageUrl.length()>0){
                //Loading the image through picasso library
                Picasso.with(foodImage.getContext()).load(imageUrl).into(foodImage);
            }else {
                foodImage.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onRecipeListItemClick(recipe);
        }
    }
}
