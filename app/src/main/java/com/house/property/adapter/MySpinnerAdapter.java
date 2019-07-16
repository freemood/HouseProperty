package com.house.property.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

/** *
 * MySpinnerAdapter * 重写以下两个方法
 * */
public class MySpinnerAdapter extends ArrayAdapter {

    public MySpinnerAdapter(@NonNull Context context, int resource, @NonNull List<String> list) {
        super(context, resource,list);
    }

    @Override
    public int getCount() {
        int i = super.getCount();
        return i > 0 ? i - 1 : i;
    }


}
