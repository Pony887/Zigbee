package com.guet.zigbee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class FouthFragment extends Fragment implements View.OnClickListener {
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
        View view = inflater.inflate(R.layout.fragment_fouth, container, false);
        ininButton(view);
        return view;
    }

    private void ininButton(View view) {
        binding = (ImageButton) view.findViewById(R.id.binding);
        electricity = (ImageButton) view.findViewById(R.id.electricity);
        callnum = (ImageButton) view.findViewById(R.id.callnum);
        version = (ImageButton) view.findViewById(R.id.version);
        suggestion = (ImageButton) view.findViewById(R.id.suggestion);

        binding.setOnClickListener(this);
        electricity.setOnClickListener(this);
        callnum.setOnClickListener(this);
        version.setOnClickListener(this);
        suggestion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.binding:
                customScan();
                break;
            case R.id.electricity:
                break;
            case R.id.callnum:
                break;
            case R.id.version:
                break;
            case R.id.suggestion:

                break;
        }
    }

    // 你也可以使用简单的扫描功能，但是一般扫描的样式和行为都是可以自定义的，这里就写关于自定义的代码了
// 你可以把这个方法作为一个点击事件
    public void customScan() {
        new IntentIntegrator(getActivity())
                .setOrientationLocked(false)
                .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                .initiateScan(); // 初始化扫描
    }
}
