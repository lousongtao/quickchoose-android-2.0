package com.shuishou.digitalmenu.ui;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.shuishou.digitalmenu.bean.Category1;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/25.
 */

public class CategoryTabAdapter extends ArrayAdapter<Category1> {
    private int resourceId;
//    private ArrayList<Category1> category1List;
//    private ChangeLanguageTextView tvName;
    private MainActivity mainActivity;
    private SparseArray<CategoryTabLayoutItem> mapCategory1LayoutItem = new SparseArray<>();

    public CategoryTabAdapter(MainActivity mainActivity, int resource, ArrayList<Category1> objects){
        super(mainActivity, resource, objects);
        this.mainActivity = mainActivity;
        resourceId = resource;
//        category1List = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Category1 c1 = getItem(position);

//        CategoryTabLayoutItem view = (CategoryTabLayoutItem)LayoutInflater.from(mainActivity).inflate(resourceId, null);
//        view.init(mainActivity, c1);
//        return view;
        if (mapCategory1LayoutItem.get(c1.getId()) == null){
            CategoryTabLayoutItem view = (CategoryTabLayoutItem)LayoutInflater.from(mainActivity).inflate(resourceId, null);
            view.init(mainActivity, c1);
            mapCategory1LayoutItem.put(c1.getId(), view);
        }
        return mapCategory1LayoutItem.get(c1.getId());
    }
}
