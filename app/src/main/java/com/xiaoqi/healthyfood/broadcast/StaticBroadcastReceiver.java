package com.xiaoqi.healthyfood.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xiaoqi.healthyfood.AppClient;
import com.xiaoqi.healthyfood.DetailActivity;
import com.xiaoqi.healthyfood.bean.ClickEvent;
import com.xiaoqi.healthyfood.utils.NotiUtil;


public class StaticBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("appstart")) {
            ClickEvent event = intent.getParcelableExtra("event");
            int intExtra = event.getPosition();
            NotiUtil.showNotifcation(context, intExtra, "今日推荐", AppClient.foods.get(intExtra).getFoodName(), DetailActivity.class, 1);//通知栏显示推荐信息
        }

    }

}
