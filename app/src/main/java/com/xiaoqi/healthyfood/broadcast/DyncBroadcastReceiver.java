package com.xiaoqi.healthyfood.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xiaoqi.healthyfood.AppClient;
import com.xiaoqi.healthyfood.CollectActivity;
import com.xiaoqi.healthyfood.bean.ClickEvent;
import com.xiaoqi.healthyfood.utils.NotiUtil;


public class DyncBroadcastReceiver extends BroadcastReceiver {

    private static int id = 10;


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("collection")) {
            ClickEvent event = intent.getParcelableExtra("event");
            int position = event.getPosition();
            NotiUtil.showNotifcation(context, position, "已收藏", AppClient.foods.get(position).getFoodName(), CollectActivity.class, id++);
        }
    }
}
