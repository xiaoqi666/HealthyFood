package com.xiaoqi.healthyfood.bean;

public class Food {

    private String foodName;//食品名字
    private String simpleType;//圆圈内容
    private String fullType;//食品类别
    private String nutrientSubstance;//营养物质
    private String color;//背景色
    private boolean isCollected;//是否被收藏


    public Food() {

    }


    public Food(String foodName, String simpleType, String fullType, String nutrientSubstance, String color) {
        this.foodName = foodName;
        this.simpleType = simpleType;
        this.fullType = fullType;
        this.nutrientSubstance = nutrientSubstance;
        this.color = color;
    }


    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }


    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getSimpleType() {
        return simpleType;
    }

    public void setSimpleType(String simpleType) {
        this.simpleType = simpleType;
    }

    public String getFullType() {
        return fullType;
    }

    public void setFullType(String fullType) {
        this.fullType = fullType;
    }

    public String getNutrientSubstance() {
        return nutrientSubstance;
    }

    public void setNutrientSubstance(String nutrientSubstance) {
        this.nutrientSubstance = nutrientSubstance;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


}
