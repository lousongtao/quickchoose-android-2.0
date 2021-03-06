package com.shuishou.digitalmenu.ui;

import android.view.View;

import com.shuishou.digitalmenu.bean.Dish;

/**
 * click the dish choose button to add it into choosed list
 * Created by Administrator on 2017/9/22.
 */

public class ClickDishPictureListener implements View.OnClickListener {
//    private static ClickDishPictureListener instance;
    private MainActivity mainActivity;
    public ClickDishPictureListener(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

//    public static ClickDishPictureListener getInstance(MainActivity mainActivity){
//        if(instance == null){
//            instance = new ClickDishPictureListener(mainActivity);
//        }
//        return instance;
//    }
//
//    public static ClickDishPictureListener getInstance(){
//        return instance;
//    }
//
//    public static void rebuildInstance(MainActivity mainActivity){
//        instance = new ClickDishPictureListener(mainActivity);
//    }
//
//    public static void release(){
//        instance = null;
//    }

    @Override
    public void onClick(View v) {
        if (v.getTag() != null && v.getTag().getClass().getName().equals(Dish.class.getName())){
            mainActivity.showDishDetailDialog((Dish)v.getTag());
        }
    }
}
