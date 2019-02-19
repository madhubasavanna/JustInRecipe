package com.example.madhupatel.justinrecipes;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.List;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepsDetail.OnStepsDetailClickListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class StepsDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    Dialog mFullScreenDialog;
    boolean mExoPlayerFullscreen;
    PlayerView mplayerView;
    SimpleExoPlayer mExoPlayer;
    Step selectedStep;
    boolean playWhenReady = false;
    int currentWindow = 0;
    long playBackPosition = 0;
    Recipe recipeData;
    List<Step> mStepsList;
    int position;
    TextView mShortDescription;
    TextView mDescription;
    Context context;
    Button mNext;
    Button mPrevious;

    private OnStepsDetailClickListener mListener;

    public StepsDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            if (getArguments() != null) {
                recipeData = getArguments().getParcelable("recipeData");
                position = getArguments().getInt("position", 0);
                mStepsList = recipeData.getSteps();
                selectedStep = mStepsList.get(position);
            }
        } else {
            recipeData = savedInstanceState.getParcelable("recipeData");
            mStepsList = recipeData.getSteps();
            position = savedInstanceState.getInt("position", 0);
            selectedStep = savedInstanceState.getParcelable("selectedStep");
            playBackPosition = savedInstanceState.getLong("playBackPosition");
            playWhenReady = savedInstanceState.getBoolean("playWhenReady");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_steps_detail, container, false);
        mplayerView = rootView.findViewById(R.id.fragment_player_view);
        mplayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        mShortDescription = rootView.findViewById(R.id.fragment_steps_short_description);
        mDescription = rootView.findViewById(R.id.fragment_steps_description);
        if (RecipeDetails.mTwoPane == false){
            mNext = rootView.findViewById(R.id.button_next);
            mPrevious = rootView.findViewById(R.id.button_previous);

            mStepsList = recipeData.getSteps();

            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // In landscape
                mShortDescription.setVisibility(View.GONE);
                mDescription.setVisibility(View.GONE);
                mNext.setVisibility(View.GONE);
                mPrevious.setVisibility(View.GONE);
                initFullscreenDialog(rootView);
                openFullscreenDialog();

            } else {
                //In portrait
                mShortDescription.setText(selectedStep.getShortDescription());
                mDescription.setText(selectedStep.getDescription());

                mNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonPressed(position + 1);
                    }
                });

                mPrevious.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonPressed(position - 1);
                    }
                });
            }
        }else {
            //Tab mode
            mShortDescription.setText(selectedStep.getShortDescription());
            mDescription.setText(selectedStep.getDescription());
        }

        return rootView;
    }

    private void initializePlayer() {
        if (mExoPlayer == null) {
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            mplayerView.setPlayer(mExoPlayer);

            if (selectedStep.getVideoURL() != "" && selectedStep.getVideoURL() != null) {
                Uri uri = Uri.parse(selectedStep.getVideoURL());
                mExoPlayer.prepare(new ExtractorMediaSource.Factory(
                        new DefaultHttpDataSourceFactory("JustInRecipes")).
                        createMediaSource(uri), true, false);
                mExoPlayer.setPlayWhenReady(playWhenReady);
                mExoPlayer.seekTo(currentWindow, playBackPosition);
            } else {

            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int pos) {
        if (mListener != null) {
            if ((pos == 0) || (pos == mStepsList.size())) {
                Toast.makeText(getContext(), "Reached the end", Toast.LENGTH_LONG).show();
            } else {
                mListener.onStepsDetailClick(pos);
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            //In portrait
            mShortDescription.setText(selectedStep.getShortDescription());
            mDescription.setText(selectedStep.getDescription());

            mNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonPressed(position + 1);
                }
            });

            mPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonPressed(position - 1);
                }
            });
        } else {
            // In landscape
            mShortDescription.setVisibility(View.GONE);
            mDescription.setVisibility(View.GONE);
            mNext.setVisibility(View.GONE);
            mPrevious.setVisibility(View.GONE);
            initFullscreenDialog(getView());
            openFullscreenDialog();
        }
    }

    //setting full screen mode for exo player
    private void initFullscreenDialog(final View view) {
        mFullScreenDialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog(view);
                super.onBackPressed();
            }
        };
    }

    private void openFullscreenDialog() {
        ((ViewGroup) mplayerView.getParent()).removeView(mplayerView);
        mFullScreenDialog.addContentView(mplayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }

    private void closeFullscreenDialog(View view) {

        ((ViewGroup) mplayerView.getParent()).removeView(mplayerView);
        mplayerView = view.findViewById(R.id.fragment_player_view);
        ((FrameLayout) view.findViewById(R.id.main_media_frame)).addView(mplayerView);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnStepsDetailClickListener) {
            mListener = (OnStepsDetailClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelable("recipeData", recipeData);
        currentState.putInt("position", position);
        currentState.putParcelable("selectedStep", selectedStep);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        currentState.putLong("playBackPosition", sharedPref.getLong("playBackPosition",0));
        currentState.putBoolean("playWhenReady", sharedPref.getBoolean("playWhenReady", false));
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("playBackPosition", mExoPlayer.getCurrentPosition());
        editor.putBoolean("playWhenReady", mExoPlayer.getPlayWhenReady());
        editor.commit();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //releasePlayer();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //releasePlayer();
        mListener = null;
    }

    public interface OnStepsDetailClickListener {
        // TODO: Update argument type and name
        void onStepsDetailClick(int position);
    }
}
