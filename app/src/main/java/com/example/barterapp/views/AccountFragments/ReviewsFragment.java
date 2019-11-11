package com.example.barterapp.views.AccountFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barterapp.R;
import com.example.barterapp.data.Offer;
import com.example.barterapp.view_models.AccountViewModels.MyReviewsViewModel;
import com.example.barterapp.view_models.ViewModelFactory;

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnHistoryInteractionListener}
 * interface.
 */
public class ReviewsFragment extends Fragment {
    private OnHistoryInteractionListener            mListener;
    private static volatile ReviewsFragment         mInstance;
    private MyReviewsViewModel                      mMyReviewsViewModel;
    private MutableLiveData<ArrayList<Offer>>       mMyOffersHistoryOffersLiveData;
    private RecyclerView                            mRecyclerView;
    private ReviewsRecyclerViewAdapter              mAdapter;

    private ReviewsFragment() {
    }

    @SuppressWarnings("unused")
    public static synchronized ReviewsFragment getInstance() {
        if (mInstance == null) {
            mInstance = new ReviewsFragment();
        }
        return mInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMyReviewsViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(MyReviewsViewModel.class);

        mMyOffersHistoryOffersLiveData = mMyReviewsViewModel.getMutableLiveDataMyOffersHistoryList();

        mMyOffersHistoryOffersLiveData.observe(this, new Observer<ArrayList<Offer>>(){
            @Override
            public void onChanged(@Nullable ArrayList<Offer> myOffers){
                if (null != myOffers){
                    // TODO: check if reallocation is better
                    mAdapter = new ReviewsRecyclerViewAdapter(myOffers,mListener);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mAdapter = new ReviewsRecyclerViewAdapter(new ArrayList<Offer>(), mListener);
            mRecyclerView.setAdapter(mAdapter);

            mMyReviewsViewModel.triggerGetMyOffersHystory();
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHistoryInteractionListener) {
            mListener = (OnHistoryInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnHistoryInteractionListener {
        void OnHistoryInteractionListener(Offer item);
    }
}
