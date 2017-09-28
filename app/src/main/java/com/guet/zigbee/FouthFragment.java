package com.guet.zigbee;

import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class FouthFragment extends Fragment{
    ImageButton binding;
    ImageButton electricity;
    ImageButton callnum;
    ImageButton version;
    ImageButton suggestion;
    public FouthFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //各种报错啊
        View view = inflater.inflate(R.layout.fragment_fouth, container, false);
        binding= (ImageButton) view.findViewById(R.id.binding);
        electricity= (ImageButton) view.findViewById(R.id.electricity);
        callnum= (ImageButton) view.findViewById(R.id.callnum);
        version= (ImageButton) view.findViewById(R.id.version);
        suggestion= (ImageButton) view.findViewById(R.id.suggestion);
        return view;
    }
}
