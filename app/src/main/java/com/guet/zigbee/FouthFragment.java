package com.guet.zigbee;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FouthFragment extends Fragment {
    public FouthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //各种报错啊
        View view = inflater.inflate(R.layout.fragment_fouth, container, false);
        return view;
    }
}
