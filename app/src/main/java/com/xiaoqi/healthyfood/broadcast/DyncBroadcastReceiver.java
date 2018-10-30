package com.xiaoqi.healthyfood.broadcast;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.xiaoqi.healthyfood.AppClient;
import com.xiaoqi.healthyfood.CollectActivity;
import com.xiaoqi.healthyfood.DetailActivity;
import com.xiaoqi.healthyfood.R;
import com.xiaoqi.healthyfood.bean.ClickEvent;
import com.xiaoqi.healthyfood.bean.Food;
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

            //====更新widget===================
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews updateView = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);//实例化RemoteView,其对应相应的Widget布局
            Intent i = new Intent(context, CollectActivity.class);
            PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            updateView.setOnClickPendingIntent(R.id.iv, pi); //设置点击事件
            Food food = AppClient.foods.get(position);
            updateView.setTextViewText(R.id.tv_content, "已收藏 " + food.getFoodName());
            ComponentName me = new ComponentName(context, NewAppWidget.class);
            appWidgetManager.updateAppWidget(me, updateView);
        }
    }
}
