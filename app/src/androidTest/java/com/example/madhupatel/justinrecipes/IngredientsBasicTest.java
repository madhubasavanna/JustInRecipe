package com.example.madhupatel.justinrecipes;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.espresso.contrib.RecyclerViewActions;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.madhupatel.justinrecipes.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class IngredientsBasicTest {
    @Rule public ActivityTestRule<MainActivity> mainActivityActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ClickRecipeListAndCheckIngredints(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.recycler_view_home)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recipe_details_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withRecyclerView(R.id.fragment_ingrident_list).atPositionOnView(0, R.id.fragment_ingrident_name))
                .check(matches(withText("Bittersweet chocolate (60-70% cacao)")));
    }

    @Test
    public void CheckStepsDetails(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.recycler_view_home)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recipe_details_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.fragment_steps_short_description)).check(matches(withText("Starting prep")));
    }
}
