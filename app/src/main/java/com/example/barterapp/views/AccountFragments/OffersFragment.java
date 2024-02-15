package com.example.barterapp.views.AccountFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

 import  com.example.barterapp.R;
import com.example.barterapp.data.Offer;
import com.example.barterapp.view_models.AccountViewModels.MyOffersViewModel;
import com.example.barterapp.view_models.ViewModelFactory;

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnOfferInteractionListener}
 * interface.
 */
public class OffersFragment extends Fragment {
    private OnOfferInteractionListener          mListener;
    private static volatile OffersFragment      mInstance;
    private MyOffersViewModel                   mMyOffersViewModel;
    private MutableLiveData<ArrayList<Offer>>   mMyOffersLiveData;
    private RecyclerView                        mRecyclerView;
    private OffersRecyclerViewAdapter           mAdapter;

    private OffersFragment() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    @SuppressWarnings("unused")
    public static synchronized OffersFragment getInstance() {
            if (mInstance == null) {
                mInstance = new OffersFragment();
            }
            return mInstance;
    }

    @Override
    public void onResume() {
        mMyOffersViewModel.triggerGetMyOffers();
        super.onResume();
    }

    /**
     * initializing class members
     *
     * @param savedInstanceState
     * @return void
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //create the view model
        mMyOffersViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(MyOffersViewModel.class);

        mMyOffersLiveData = mMyOffersViewModel.getMutableLiveDataMyOffersList();

        mMyOffersLiveData.observe(this, new Observer<ArrayList<Offer>>(){
            @Override
            public void onChanged(@Nullable ArrayList<Offer> myOffers){
                if (null != myOffers){
                    mAdapter.setValues(myOffers);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mAdapter = new OffersRecyclerViewAdapter(new ArrayList<>(),mListener);
            mRecyclerView.setAdapter(mAdapter);
            mMyOffersViewModel.triggerGetMyOffers();
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOfferInteractionListener) {
            mListener = (OnOfferInteractionListener) context;
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
    public interface OnOfferInteractionListener {
        /**
         * On offer interaction listener.
         *
         * @param item the item
         */
        void OnOfferInteractionListener(Offer item);
    }
}
