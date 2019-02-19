package com.example.madhupatel.justinrecipes;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.madhupatel.justinrecipes.R;
import com.example.madhupatel.justinrecipes.Recipe;
import com.example.madhupatel.justinrecipes.WidgetService;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeListWidget extends AppWidgetProvider {
    static Recipe recipe = null;
    static String name = null;
    static int recipeId = 0;
    Intent dataIntent;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        // Construct the RemoteViews object
        if(recipe != null && name != null){
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_list_widget);

            views.setTextViewText(R.id.widget_recipe_name, name);
            Intent intent = new Intent(context, WidgetService.class);
            intent.putExtra("recipeData", recipe);
            intent.putExtra("recipeId", recipeId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            views.setRemoteAdapter(R.id.widget_ingredients_list, intent);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }else {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_list_widget);

            views.setTextViewText(R.id.widget_recipe_name, "Select any recipe in the app");
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    public static void updateIngredientsList(Context context, AppWidgetManager appWidgetManager,
                                             Recipe recipes, int[] appWidgetIds){
        recipe = recipes;
        name = recipes.getName();
        recipeId = recipes.getId();
        for (int appWidgetId: appWidgetIds){
            updateAppWidget(context,appWidgetManager,appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

