package com.example.madhupatel.justinrecipes;

import android.content.Intent;
import android.widget.RemoteViewsService;

import static com.example.madhupatel.justinrecipes.RecipeListWidget.recipe;

public class WidgetService extends RemoteViewsService {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new WidgetlistProvider(this.getApplicationContext(), recipe.getIngredients()));
    }
}