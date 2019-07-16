package com.house.property.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.house.property.R;
import com.house.property.model.task.activity.TaskBolckDetailsInfoActivity;
import com.house.property.model.task.entity.GisObjVO;

import java.util.List;

import static com.house.property.utils.TaskUtils.reTextNull;

public class WindowBlockAdapter implements AMap.InfoWindowAdapter, AMap.OnMarkerClickListener,
        AMap.OnInfoWindowClickListener {

    private Context context;
    private static final String TAG = "WindowAdapter";
    private List<GisObjVO> marketList;

    public WindowBlockAdapter(Context context, List<GisObjVO> marketList) {
        this.context = context;
        this.marketList = marketList;
    }


    @Override
    public View getInfoWindow(Marker marker) {
        //关联布局
        View view = LayoutInflater.from(context).inflate(R.layout.layout_block_info_item, null);
        //标题
        TextView title = view.findViewById(R.id.info_title);
        //楼栋号
        TextView bolckNumber = view.findViewById(R.id.bolck_number);
        //梯户
        TextView household = view.findViewById(R.id.household);
        //缺少信息
        TextView defectNumber = view.findViewById(R.id.info_defect_number);

        bolckNumber.setText(marker.getTitle());
        household.setText(marker.getSnippet());
//        String testStr = context.getResources().getString(R.string.house_task_area_defect_bolck);
//        String result = String.format(testStr, "3");
//        defectNumber.setText(result);
        return view;
    }


    //如果用自定义的布局，不用管这个方法,返回null即可
    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    // marker 对象被点击时回调的接口
    // 返回 true 则表示接口已响应事件，否则返回false
    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }

    //绑定信息窗点击事件
    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(context, TaskBolckDetailsInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("buildId", getBuildId(marker)[0]);
        bundle.putString("taskId", getBuildId(marker)[1]);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private String[] getBuildId(Marker marker) {
        String[] id = new String[2];
        LatLng latLng = marker.getPosition();
        String y = reTextNull(String.valueOf(latLng.latitude));
        String x = reTextNull(String.valueOf(latLng.longitude));

        for (GisObjVO gisObjVO : marketList) {
            if ((x.equals(gisObjVO.getCenterX()) && y.equals(gisObjVO.getCenterY())) || (x.equals(gisObjVO.getLabelX()) && y.equals(gisObjVO.getLabelY()))) {
                id[0] = gisObjVO.getBuildId();
                id[1] = gisObjVO.getTaskId();
                continue;
            }
        }
        return id;
    }


}
