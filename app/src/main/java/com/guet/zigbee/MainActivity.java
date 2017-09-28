package com.guet.zigbee;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Notification.Builder;

import Dao.DataBase;

import static android.support.v4.view.PagerAdapter.POSITION_NONE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NotificationManager manager;
    private int Notification_ID;
    //辅助判断是否一键呼救
    private boolean iscall = true;
    //获取activity
    private Activity activity = this;
    String[] titles = new String[]{"身体数据", "运动睡眠", "位置信息", "我"};
    private ImageView item_weixin, item_tongxunlu, item_faxian, item_me;
    private Button call;
    private TextView title;
    private ViewPager vp;
    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private ThreeFragment threeFragment;
    private FouthFragment fouthFragment;
    private MapFragment mapFragment;
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
        //通知栏管理器
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        定时
        TimerTask callTimeTask = new TimerTask() {
            @Override
            public void run() {
                DataBase db = new DataBase();
                ArrayList<Data> dataList = db.TheSqlConnection();
                if (!dataList.isEmpty() && dataList.get(dataList.size() - 1).getCall().equals("call") && iscall) {
//                    VibratorUtil.Vibrate(activity,1000);
                    showNotification();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    iscall = false;
                } else if (!dataList.isEmpty() && dataList.get(dataList.size() - 1).getCall().equals("nocall")) {
                    iscall = true;
                }
            }
        };
        Timer infoTimer = new Timer();
        infoTimer.schedule(callTimeTask, 0, 6000);

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
     *
     */
    private void showNotification() {
        // TODO Auto-generated method stub
        Notification.Builder builder = new Builder(this);
        builder.setSmallIcon(R.drawable.icon);//设置图标
        builder.setTicker("一键呼救");//手机状态栏的提示
        builder.setContentTitle("一键呼救");//设置标题
        builder.setContentText("有老人一键呼救,点击查看");//设置通知内容
        builder.setWhen(System.currentTimeMillis());//设置通知时间
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);//点击后的意图
        builder.setDefaults(Notification.DEFAULT_LIGHTS);//设置指示灯
        builder.setDefaults(Notification.DEFAULT_SOUND);//设置提示声音
        builder.setDefaults(Notification.DEFAULT_VIBRATE);//设置震动
        Notification notification = builder.build();//4.1以上，以下要用getNotification()
        manager.notify(Notification_ID, notification);
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
        call = (Button) findViewById(R.id.call);

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
        mapFragment = new MapFragment();
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
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "15207732195"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
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

    public void changeThird(int m) {
        if (m == 0) {
            mFragmentList.remove(3);
            mFragmentList.add(3, threeFragment);
        }
        if (m == 1) {
            mFragmentList.remove(3);
            mFragmentList.add(3, mapFragment);
            mFragmentAdapter.notifyDataSetChanged();
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

        //test
        /*@Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container,position);
            return fragment;
        }
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;//是返回POSITION_NONE
        }*/
    }

}
