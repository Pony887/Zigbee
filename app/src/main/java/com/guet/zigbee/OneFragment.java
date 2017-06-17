package com.guet.zigbee;

import android.accounts.Account;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OneFragment extends Fragment {

    private static final int REFRESHINTERVAL  = 1;    //刷新时间间隔（分钟）
    private int heart=70;
    private int blood1=130;
    private int blood2=80;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        return view;
    }

    //视频测试用
    public void showDeleteDiaog() {
        //这里的构造函数使用两个参数，第二个参数即为设置样式
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialog);
        builder.setNegativeButton(".", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.show();
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
        infoTimer.schedule(infoTimerTask ,0,REFRESHINTERVAL*6000);

    }

    private class FetchInfoTask extends AsyncTask<Void,Void,List<Data>>{
        private List<Data> dataList;
        @Override
        protected List<Data> doInBackground(Void... params) {
            try {
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
            }
            return dataList;
        }

        @Override
        protected void onPostExecute(List<Data> dataList) {
            TextView heartView = (TextView)getView().findViewById(R.id.heart);
            TextView bloodView = (TextView)getView().findViewById(R.id.bloodhigh);
            TextView bloodView1= (TextView) getView().findViewById(R.id.bloodlow);
            Button button= (Button) getView().findViewById(R.id.testButton);
            Random rd = new Random();
            heartView.setText(String.valueOf(heart));
            bloodView.setText(String.valueOf(blood1));
            bloodView1.setText(String.valueOf(blood2));
            if (blood1>150||blood2>100||heart>85)
            {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext(),R.style.AlertDialog1);
                builder1.setNegativeButton(".", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder1.show();
            }
            heart=heart+5;
            blood1=blood1+5;
            blood2=blood2+6;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteDiaog();
                }
            });
//            heartView.setText(String.valueOf(rd.nextInt(90)+10));
//            bloodView.setText(String.valueOf(rd.nextInt(989)+10));
//            bloodView1.setText(String.valueOf(rd.nextInt(90)+10));
        }
    }
    private List<Data> parseJSONWithGSON(String jsonData)
    {
        Gson gson=new Gson();
        List<Data> dataList=gson.fromJson(jsonData,new TypeToken<List<Data>>(){}.getType());
        for (int i=0;i<dataList.size();i++)
        {
            Log.d("MainActivity","Id is "+dataList.get(i).getId());
            Log.d("MainActivity","Name is "+dataList.get(i).getName());
            Log.d("MainActivity","Version is "+dataList.get(i).getVersion());
        }
        return dataList;
    }
}
