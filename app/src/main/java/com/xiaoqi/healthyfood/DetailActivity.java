package com.xiaoqi.healthyfood;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoqi.healthyfood.bean.ClickEvent;
import com.xiaoqi.healthyfood.bean.Food;
import com.xiaoqi.healthyfood.broadcast.DyncBroadcastReceiver;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_ib_back)
    ImageButton detailIbBack;
    @BindView(R.id.detail_tv_name)
    TextView detailTvName;
    @BindView(R.id.detail_ib_star)
    ImageButton detailIbStar;
    @BindView(R.id.detail_rl)
    RelativeLayout detailRl;
    @BindView(R.id.detail_tv_type)
    TextView detailTvType;
    @BindView(R.id.detail_tv_yingyang)
    TextView detailTvYingyang;
    @BindView(R.id.detail_ib_collect)
    ImageButton detailIbCollect;
    @BindView(R.id.detail_lv_detail)
    ListView detailLvDetail;
    private Food food;


    private boolean isLightStar = true;//记录小星星的状态
    private String[] desc;
    private int position;
    private DyncBroadcastReceiver dyncBroadcastReceiver;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        /**
         * 注册广播
         */
        dyncBroadcastReceiver = new DyncBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter("collection");
        registerReceiver(dyncBroadcastReceiver, intentFilter);
        intent = getIntent();
        initData();
        initAdapter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册
        unregisterReceiver(dyncBroadcastReceiver);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.intent = intent;
    }

    private void initData() {
        ClickEvent event = intent.getParcelableExtra("event");
        position = event.getPosition();
        food = AppClient.foods.get(position);
        detailTvName.setText(food.getFoodName());
        detailTvType.setText(food.getFullType());
        detailTvYingyang.setText("富含  " + food.getNutrientSubstance());
    }

    /**
     * 用butterknife绑定事件
     *
     * @param view 哪个组件调用的
     */
    @OnClick({R.id.detail_ib_star, R.id.detail_ib_collect, R.id.detail_ib_back})
    void onclick(View view) {
        switch (view.getId()) {
            case R.id.detail_ib_star:
                if (isLightStar) {//星星是不是空心的，空心为true
                    detailIbStar.setBackgroundResource(R.drawable.full_star);
                } else {
                    detailIbStar.setBackgroundResource(R.drawable.empty_star);
                }
                isLightStar = !isLightStar;//状态切换过后，状态置反
                break;
            case R.id.detail_ib_collect:
                if (food.isCollected()) {
                    startActivity(new Intent(DetailActivity.this, CollectActivity.class));
                } else {
                    food.setCollected(true);
                    //  发送动态广播，已收藏
                    Intent intent = new Intent("collection");
                    intent.putExtra("event", new ClickEvent("动态广播", position));
                    sendBroadcast(intent);
                    //发送订阅消息
                    EventBus.getDefault().post(food);
                }
                break;
            case R.id.detail_ib_back:
                finish();
                break;
        }
    }


    private void initAdapter() {
        desc = new String[]{"分享信息", "不感兴趣", "查看更多信息", "出错反馈"};
        detailLvDetail.setAdapter(new MyListViewAdapter());
    }


    class MyListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return desc.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(DetailActivity.this, R.layout.item_lv_detail, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvText.setText(desc[position]);
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.tv_text)
            TextView tvText;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


}
