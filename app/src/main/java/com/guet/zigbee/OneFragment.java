package com.guet.zigbee;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Dao.DataBase;

public class OneFragment extends Fragment{

    private static final int REFRESHINTERVAL  = 1;    //刷新时间间隔（分钟）
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        //初始化下拉刷新组件,添加监听
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) this.getActivity().findViewById(R.id.srl);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FetchInfoTask().execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.CYAN, Color.RED);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //定时刷新心率，血压
        TimerTask infoTimerTask = new TimerTask() {
            @Override
            public void run() {
                new FetchInfoTask().execute();
            }
        };
        Timer infoTimer = new Timer();
        infoTimer.schedule(infoTimerTask, 0, REFRESHINTERVAL * 60000);
    }

    private class FetchInfoTask extends AsyncTask<Void,Void,ArrayList<Data>>{
        @Override
        protected ArrayList<Data> doInBackground(Void... params) {
            DataBase db=new DataBase();
            ArrayList<Data> dataList=db.TheSqlConnection();
            return dataList;
        }

        @Override
        protected void onPostExecute(ArrayList<Data> dataList) {
            if (getView() != null) {
                TextView heartView = (TextView)getView().findViewById(R.id.heart);
                TextView bloodView = (TextView)getView().findViewById(R.id.bloodhigh);
                TextView bloodView1= (TextView) getView().findViewById(R.id.bloodlow);

                heartView.setText(dataList.get(dataList.size()-1).getHeart());
                bloodView.setText(String.valueOf(dataList.get(dataList.size()-1).getBloodlow()));
                bloodView1.setText(String.valueOf(dataList.get(dataList.size()-1).getBloodhigh()));
            }
        }
    }
}
