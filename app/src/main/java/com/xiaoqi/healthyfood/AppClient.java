package com.xiaoqi.healthyfood;

import android.app.Application;


import com.xiaoqi.healthyfood.bean.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * activity间共享数据
 */
public class AppClient extends Application {

    public static final List<Food> foods = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }

    private void initData() {
        foods.add(new Food("大豆", "粮", "粮食", "蛋白质", "#BB4C3B"));
        foods.add(new Food("十字花科蔬菜", "蔬", "蔬菜", "维生素C", "#C48D30"));
        foods.add(new Food("牛奶", "饮", "饮品", "钙", "#4469B0"));
        foods.add(new Food("海鱼", "肉", "肉食", "蛋白质", "#20A17B"));
        foods.add(new Food("菌菇类", "蔬", "蔬菜", "微量元素", "#BB4C3B"));
        foods.add(new Food("番茄", "蔬", "蔬菜", "番茄红素", "#4469B0"));
        foods.add(new Food("胡萝卜", "蔬", "蔬菜", "胡萝卜素", "#20A17B"));
        foods.add(new Food("荞麦", "粮", "粮食", "膳食纤维", "#BB4C3B"));
        foods.add(new Food("鸡蛋", "杂", "杂", "几乎所有营养物质", "#C48D30"));
    }

}
