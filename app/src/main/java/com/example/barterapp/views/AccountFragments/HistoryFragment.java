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
import com.example.barterapp.view_models.AccountViewModels.MyHistoryViewModel;
import com.example.barterapp.view_models.ViewModelFactory;

import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnHistoryInteractionListener}
 * interface.
 */
public class HistoryFragment extends Fragment {
    private OnHistoryInteractionListener            mListener;
    private static volatile HistoryFragment         mInstance;
    private MyHistoryViewModel                      mMyHistoryViewModel;
    private MutableLiveData<ArrayList<Offer>>       mMyHistoryLiveData;

    private HistoryFragment() {
    }

    @SuppressWarnings("unused")
    public static synchronized HistoryFragment getInstance() {
        if (mInstance == null) {
            mInstance = new HistoryFragment();
        }
        return mInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMyHistoryViewModel = ViewModelProviders.of(this, new ViewModelFactory())
                .get(MyHistoryViewModel.class);

        mMyHistoryLiveData = mMyHistoryViewModel.getMutableLiveDataMyHistoryList();

        mMyHistoryLiveData.observe(this, new Observer<ArrayList<Offer>>(){
            @Override
            public void onChanged(@Nullable ArrayList<Offer> myOffers){
                if (null != myOffers){
                    //ToDo: populate my history
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new HistoryRecyclerViewAdapter(mListener));
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
