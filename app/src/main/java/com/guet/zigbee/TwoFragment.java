package com.guet.zigbee;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TwoFragment extends Fragment {

    private static final int REFRESHINTERVAL = 1;    //刷新时间间隔（分钟）

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定时刷新心率，血压
        TimerTask infoTimerTask = new TimerTask() {
            @Override
            public void run() {
                new TwoFragment.FetchInfoTask().execute();
            }
        };
        Timer infoTimer = new Timer();
        infoTimer.schedule(infoTimerTask, 0, REFRESHINTERVAL * 60000);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //初始化下拉刷新组件,添加监听
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) this.getActivity().findViewById(R.id.sr2);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new TwoFragment.FetchInfoTask().execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.CYAN, Color.RED);
    }

    private class FetchInfoTask extends AsyncTask<Void, Void, List<Data>> {
        private List<Data> dataList;

        @Override
        //后台获取数据
        protected List<Data> doInBackground(Void... params) {
           /* try {
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url("http://112.74.210.103/get_data.json")
                        .build();
                Response response=client.newCall(request).execute();
                String responseData=response.body().string();
                //解析json数据;
                dataList = parseJSONWithGSON(responseData);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            return dataList;
        }

        @Override
        protected void onPostExecute(List<Data> dataList) {
            if (getView() != null) {
                TextView exerView = (TextView) getView().findViewById(R.id.textview_exercise);

                Random rd = new Random();
                exerView.setText(String.valueOf(rd.nextInt(20) + Integer.valueOf(exerView.getText().toString())));
            }
        }
    }

}

