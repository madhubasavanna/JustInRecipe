package com.example.madhupatel.justinrecipes;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StepsDetailsActivity extends AppCompatActivity implements StepsDetail.OnStepsDetailClickListener {
    static Recipe recipeData;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_details);
        if(savedInstanceState == null){
            recipeData = getIntent().getParcelableExtra("recipeData");
            position = getIntent().getIntExtra("position", 0);

            StepsDetail stepsDetail = new StepsDetail();
            Bundle bundle = new Bundle();
            bundle.putParcelable("recipeData", recipeData);
            bundle.putInt("position", position);
            stepsDetail.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.fragment_steps_container, stepsDetail).commit();
        }else {
            // do nothing - fragment is recreated automatically
        }
    }

    @Override
    public void onStepsDetailClick(int p) {
        position = p;

        StepsDetail stepsDetail = new StepsDetail();
        Bundle bundle = new Bundle();
        bundle.putParcelable("recipeData", recipeData);
        bundle.putInt("position", p);
        stepsDetail.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_steps_container, stepsDetail).addToBackStack("steps Detail fragment")
                .commit();
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
}
