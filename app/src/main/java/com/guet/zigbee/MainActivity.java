package com.guet.zigbee;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String[] titles = new String[]{"身体数据", "运动睡眠", "位置信息", "我"};
    private ImageView  item_weixin, item_tongxunlu, item_faxian,item_me;
    private Button call;
    private TextView title;
    private ViewPager vp;
    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private ThreeFragment threeFragment;
    private FouthFragment fouthFragment;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除工具栏
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initViews();
        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        vp.setOffscreenPageLimit(4);//ViewPager的缓存为4帧
        vp.setAdapter(mFragmentAdapter);
        item_weixin.setImageResource(R.drawable.health1);
        vp.setCurrentItem(0);//初始设置ViewPager选中第一帧

        //ViewPager的监听事件
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /*此方法在页面被选中时调用*/
                title.setText(titles[position]);
                changeTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /*此方法是在状态改变的时候调用，其中arg0这个参数有三种状态（0，1，2）。
                arg0 ==1的时辰默示正在滑动，
                arg0==2的时辰默示滑动完毕了，
                arg0==0的时辰默示什么都没做。*/
            }
        });
    }

    /**
     * 初始化布局View
     */
    private void initViews() {
        title = (TextView) findViewById(R.id.title);
        item_weixin = (ImageView) findViewById(R.id.item_weixin);
        item_tongxunlu = (ImageView) findViewById(R.id.item_tongxunlu);
        item_faxian = (ImageView) findViewById(R.id.item_faxian);
        item_me = (ImageView) findViewById(R.id.item_me);
        call= (Button) findViewById(R.id.call);

        item_weixin.setOnClickListener(this);
        item_tongxunlu.setOnClickListener(this);
        item_faxian.setOnClickListener(this);
        item_me.setOnClickListener(this);
        call.setOnClickListener(this);

        vp = (ViewPager) findViewById(R.id.mainViewPager);
        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();
        threeFragment = new ThreeFragment();
        fouthFragment = new FouthFragment();
        //给FragmentList添加数据
        mFragmentList.add(oneFragment);
        mFragmentList.add(twoFragment);
        mFragmentList.add(threeFragment);
        mFragmentList.add(fouthFragment);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_weixin:
                vp.setCurrentItem(0, true);
                break;
            case R.id.item_tongxunlu:
                vp.setCurrentItem(1, true);
                break;
            case R.id.item_faxian:
                vp.setCurrentItem(2, true);
                break;
            case R.id.item_me:
                vp.setCurrentItem(3, true);
                break;
            case R.id.call:
                sendReqyestWithOkHttp();
                break;
            //拍视屏测试用
//            case R.id.testButton:
////                sendReqyestWithOkHttp();
//                showDeleteDiaog();
//                break;
        }
    }

    /*
     *由ViewPager的滑动修改底部导航Text的颜色
     */
    private void changeTextColor(int position) {
        if (position == 0) {
            item_weixin.setImageResource(R.drawable.health);
            item_tongxunlu.setImageResource(R.drawable.sport);
            item_faxian.setImageResource(R.drawable.postion);
            item_me.setImageResource(R.drawable.data);
        } else if (position == 1) {
            item_weixin.setImageResource(R.drawable.health1);
            item_tongxunlu.setImageResource(R.drawable.sport1);
            item_faxian.setImageResource(R.drawable.postion);
            item_me.setImageResource(R.drawable.data);
        } else if (position == 2) {
            item_weixin.setImageResource(R.drawable.health1);
            item_tongxunlu.setImageResource(R.drawable.sport);
            item_faxian.setImageResource(R.drawable.postion1);
            item_me.setImageResource(R.drawable.data);
        } else if (position == 3) {
            item_weixin.setImageResource(R.drawable.health1);
            item_tongxunlu.setImageResource(R.drawable.sport);
            item_faxian.setImageResource(R.drawable.postion);
            item_me.setImageResource(R.drawable.data1);
        }
    }

    //Okhttp获取数据
    private void sendReqyestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("http://1.myfirstpay.applinzi.com/wrist/data_deal/select.php")
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    //解析json数据
                    parseJSONWithGSON(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //解析并显示json数据
    private void parseJSONWithGSON(String jsonData)
    {
        Gson gson=new Gson();
        List<Data> dataList=gson.fromJson(jsonData,new TypeToken<List<Data>>(){}.getType());
        for (int i=0;i<dataList.size();i++)
        {
            Log.d("MainActivity","Id is "+dataList.get(i).getHeart());
            Log.d("MainActivity","Name is "+dataList.get(i).getBloodhigh());
            Log.d("MainActivity","Version is "+dataList.get(i).getBloodlow());
        }
    }

    public class FragmentAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<Fragment>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
