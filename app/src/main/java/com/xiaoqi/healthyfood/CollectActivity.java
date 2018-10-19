package com.xiaoqi.healthyfood;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoqi.healthyfood.bean.ClickEvent;
import com.xiaoqi.healthyfood.bean.Food;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

public class CollectActivity extends AppCompatActivity {

    @BindView(R.id.collect_lv_collect)
    ListView collectLvCollect;
    @BindView(R.id.collect_fab)
    FloatingActionButton collectFab;
    private List<Food> collctFoods;
    private MyCollectAdapter myCollectAdapter;
    private boolean isFisrt = true;

    @Override
    protected void onStart() {
        super.onStart();
        if (isFisrt) {
            isFisrt = false;
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        initData();
    }

    /**
     * item的点击事件
     *
     * @param position
     */

    @OnItemClick(R.id.collect_lv_collect)
    void onItemClick(int position) {
        Food food = collctFoods.get(position);
        Intent intent = new Intent(CollectActivity.this, DetailActivity.class);
        intent.putExtra("event", new ClickEvent("dianji", AppClient.foods.indexOf(food)));
        startActivity(intent);
    }

    /**
     * item的长按事件
     *
     * @param position
     * @return
     */
    @OnItemLongClick(R.id.collect_lv_collect)
    boolean onItemLongClick(int position) {
        showDeleteDialog(position);
        return true;
    }


    /**
     * 绑定点击事件
     */
    @OnClick(R.id.collect_fab)
    void onclick() {
        startActivity(new Intent(CollectActivity.this, MainActivity.class));
    }


    private void initData() {
        collctFoods = new ArrayList<>();
        int size = AppClient.foods.size();
        for (int i = 0; i < size; i++) {
            Food food = AppClient.foods.get(i);
            if (food.isCollected())
                collctFoods.add(food);
        }

        myCollectAdapter = new MyCollectAdapter();
        collectLvCollect.setAdapter(myCollectAdapter);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(Food food) {
        if (collctFoods != null && myCollectAdapter != null) {
            if (food.isCollected()) {
                collctFoods.add(food);
            } else {
                collctFoods.remove(food);
            }
            myCollectAdapter.notifyDataSetChanged();
        }
    }

    private void showDeleteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除");
        builder.setMessage("确定删除" + collctFoods.get(position).getFoodName() + "?");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Food food = collctFoods.get(position);
                food.setCollected(false);
                collctFoods.remove(food);
                myCollectAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        builder.show();
    }


    /**
     * 适配器
     */
    class MyCollectAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return collctFoods.size();
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
                convertView = View.inflate(CollectActivity.this, R.layout.item_lv_food, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Food food = collctFoods.get(position);
            holder.tvName.setText(food.getFoodName());
            holder.tvType.setText(food.getSimpleType());
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.tv_type)
            TextView tvType;
            @BindView(R.id.tv_name)
            TextView tvName;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


}
