package com.example.madhupatel.justinrecipes;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.madhupatel.justinrecipes.Ingredient;
import com.example.madhupatel.justinrecipes.R;

import java.util.List;

public class WidgetlistProvider implements RemoteViewsService.RemoteViewsFactory {
    private List<Ingredient> ingredients;
    private Context context;

    public WidgetlistProvider(Context context, List<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.widget_list_item);
        Ingredient listItem = ingredients.get(position);
        remoteView.setTextViewText(R.id.widget_ingredient_name, listItem.getIngredient());
        remoteView.setTextViewText(R.id.widget_ingredient_quantiy, listItem.getQuantity().toString());
        remoteView.setTextViewText(R.id.widget_ingredient_measure, listItem.getMeasure());

        return remoteView;
    }

}
