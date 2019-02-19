package com.example.madhupatel.justinrecipes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Ingredient} and makes a call to the
 * TODO: Replace the implementation with code for your data type.
 */
public class MyIngredientsListRecyclerViewAdapter extends RecyclerView.Adapter<MyIngredientsListRecyclerViewAdapter.ViewHolder> {

    private final List<Ingredient> mValues;

    public MyIngredientsListRecyclerViewAdapter(List<Ingredient> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ingredientslist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mIngrident = mValues.get(position);
        holder.mName.setText(mValues.get(position).getIngredient());
        holder.mMeasure.setText(mValues.get(position).getMeasure());
        holder.mQuantity.setText(mValues.get(position).getQuantity().toString());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public Ingredient mIngrident;
        public final TextView mName;
        public final TextView mMeasure;
        public final TextView mQuantity;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = view.findViewById(R.id.fragment_ingrident_name);
            mMeasure = view.findViewById(R.id.fragment_ingrident_measure);
            mQuantity = view.findViewById(R.id.fragment_ingrident_quantity);
        }
    }
}
