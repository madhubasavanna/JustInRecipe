package com.example.madhupatel.justinrecipes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetailsAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final int STEPS = 0, INGREDIENTS =1, BUTTON_ADDLIST_TO_WIDGET = 2;
    List<Step> steps;
    String Ingredient;
    int position;
    StepsItemClickListener stepsItemClickListener;
    IngredientsListClickListener ingredientsClickListener;
    UpdateWidgetClickListener updateWidgetClickListener;

    public interface StepsItemClickListener {
        void onStepsClicked(int position, Step step);
    }

    public interface IngredientsListClickListener {
        void onIngredientsClicked(int position);
    }

    public interface UpdateWidgetClickListener{
        void OnWidgetClick();
    }

    public RecipeDetailsAdapter(List<Step> steps, StepsItemClickListener stepsItemClickListener,
                                IngredientsListClickListener ingredientsListClickListener,
                                UpdateWidgetClickListener updateWidgetClickListener) {
        this.steps = steps;
        this.stepsItemClickListener = stepsItemClickListener;
        this.ingredientsClickListener = ingredientsListClickListener;
        this.updateWidgetClickListener = updateWidgetClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
           return INGREDIENTS;
        else if(position > 0 && position < steps.size())
            return STEPS;
        else if(position == steps.size()){
            return BUTTON_ADDLIST_TO_WIDGET;
        }
        return -1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.recipe_card_view, viewGroup, false);
        if(viewType == STEPS){
            return new RecipeDetailsViewHolder(view);
        }else if(viewType == INGREDIENTS){
            return new IngredientsDetailsViewHolder(view);
        }
        else if(viewType == BUTTON_ADDLIST_TO_WIDGET){
            LayoutInflater inflater1 = LayoutInflater.from(viewGroup.getContext());
            View view1 = inflater1.inflate(R.layout.button_layout, viewGroup, false);
            return new UpdateWidgetViewHolder(view1);
        }
        return new RecipeDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        this.position = i;
        if(viewHolder instanceof RecipeDetailsViewHolder){
            ((RecipeDetailsViewHolder) viewHolder).step = steps.get(i);
            ((RecipeDetailsViewHolder) viewHolder).bind(i);
        }else if(viewHolder instanceof IngredientsDetailsViewHolder){
            ((IngredientsDetailsViewHolder) viewHolder).bind(i);
        }else if(viewHolder instanceof UpdateWidgetViewHolder){
            ((UpdateWidgetViewHolder) viewHolder).btnWidget.setText("ADD INGREDIENTS LIST TO WIDGETS");
        }
    }

    @Override
    public int getItemCount() {
        return steps.size() + 1;
    }

    public class UpdateWidgetViewHolder extends ViewHolder implements View.OnClickListener{
        public TextView btnWidget;

        public UpdateWidgetViewHolder(@NonNull View itemView) {
            super(itemView);
            btnWidget = itemView.findViewById(R.id.button_add_list_widget);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            updateWidgetClickListener.OnWidgetClick();
        }
    }

    public class IngredientsDetailsViewHolder extends ViewHolder implements View.OnClickListener{
        TextView ing;
        ImageView img;
        View ingredients;

        public IngredientsDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredients = itemView;
            ing = itemView.findViewById(R.id.recipe_title);
            img = itemView.findViewById(R.id.recipe_image);
            itemView.setOnClickListener(this);
        }

        public void bind(int position){
            ing.setText("Ingredients");
            img.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View v) {
            ingredientsClickListener.onIngredientsClicked(getAdapterPosition());
        }
    }

    public class RecipeDetailsViewHolder extends ViewHolder implements View.OnClickListener
    {
        View selectedView;
        Step step;
        ImageView stepImg;
        TextView stepDesc;
        String descTitle;
        String stepImgUrl;

        public RecipeDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            selectedView = itemView;
            stepImg = itemView.findViewById(R.id.recipe_image);
            stepDesc = itemView.findViewById(R.id.recipe_title);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            RecipeDetailsAdapter.this.position = position;
            descTitle = steps.get(position).getShortDescription();
            stepDesc.setText(descTitle);
            stepImgUrl = steps.get(position).getThumbnailURL();

            if (stepImgUrl != "" && stepImgUrl.length() > 0) {
                //Loading the image through picasso library
                Picasso.with(stepImg.getContext()).load(stepImgUrl).into(stepImg);
            } else {
                stepImg.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            stepsItemClickListener.onStepsClicked(getAdapterPosition(), step);
        }
    }
}
