package com.xiaoqi.healthyfood;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaoqi.healthyfood.bean.ClickEvent;
import com.xiaoqi.healthyfood.bean.Food;
import com.xiaoqi.healthyfood.bean.LongClickEvent;
import com.xiaoqi.healthyfood.broadcast.StaticBroadcastReceiver;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_recyclerview)
    RecyclerView mainRecyclerview;
    @BindView(R.id.main_fab)
    FloatingActionButton mainFab;

    boolean isFirstRegist = true;//是否是第一次注册EventBus
    private MainRecyclerviewAdapter mainAdapter;


    //生命周期start方法
    @Override
    protected void onStart() {
        super.onStart();
        if (isFirstRegist) {
            isFirstRegist = false;
            EventBus.getDefault().register(this);//注册事件总线
        }
    }


    //生命周期activity销毁方法
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);//通过注解的方式findViewById
        initData();//初始化数据

        /**
         * 发送广播
         */
        Intent intent = new Intent("appstart");
        int position = (int) (Math.random() * AppClient.foods.size());
        intent.putExtra("event", new ClickEvent("发送静态广播", position));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//判断版本号是否大于26
            intent.setComponent(new ComponentName(this, StaticBroadcastReceiver.class));
        }
        sendBroadcast(intent);
    }


    /**
     * 初始化
     */
    private void initData() {
        mainAdapter = new MainRecyclerviewAdapter();
        mainRecyclerview.setAdapter(mainAdapter);
        mainRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mainRecyclerview.setItemAnimator(new DefaultItemAnimator());//设置默认动画，其实不设置也会自动设置
    }


    @OnClick(R.id.main_fab)
    void fabOnclick() {
        //跳转到收藏页面
        startActivity(new Intent(MainActivity.this, CollectActivity.class));
    }

    /**
     * 单击事件的处理
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    void onClickEvent(ClickEvent event) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("event", event);
        startActivity(intent);
    }

    /**
     * 长按事件的处理
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    void onLongClickEvent(LongClickEvent event) {
        AppClient.foods.remove(AppClient.foods.get(event.getPosition()));
        mainAdapter.notifyItemRemoved(event.getPosition());
    }


    /**
     * Recyclerview的适配器
     */
    class MainRecyclerviewAdapter extends RecyclerView.Adapter<MainRecyclerviewAdapter.Holder> {

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = View.inflate(MainActivity.this, R.layout.item_lv_food, null);
            Holder holder = new Holder(view);
            return holder;
        }

        //显示数据
        @Override
        public void onBindViewHolder(@NonNull Holder holder, int i) {
            Food food = AppClient.foods.get(i);
            holder.tv_name.setText(food.getFoodName());
            holder.tv_type.setText(food.getSimpleType());
        }


        @Override
        public int getItemCount() {
            return AppClient.foods.size();
        }


        class Holder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_type)
            TextView tv_type;
            @BindView(R.id.tv_name)
            TextView tv_name;

            public Holder(@NonNull View view) {
                super(view);
                ButterKnife.bind(this, view);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //发送点击事件
                        EventBus.getDefault().post(new ClickEvent("单击", getLayoutPosition()));
                    }
                });

                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //发送长按事件
                        EventBus.getDefault().post(new LongClickEvent("长按", getLayoutPosition()));
                        return true;
                    }
                });
            }
        }
    }
}
