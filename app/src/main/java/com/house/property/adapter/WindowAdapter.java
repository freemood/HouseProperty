package com.house.property.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.house.property.R;
import com.house.property.model.main.entity.CommunityBriefBean;
import com.house.property.model.main.entity.TaskBean;
import com.house.property.model.task.activity.TaskDetailsInfoActivity;
import com.house.property.model.task.entity.CommDetailsQueryVO;

import org.greenrobot.eventbus.EventBus;

import static com.house.property.utils.TaskUtils.reTextNull;

public class WindowAdapter implements AMap.InfoWindowAdapter, AMap.OnMarkerClickListener,
        AMap.OnInfoWindowClickListener {

    private Context context;
    private static final String TAG = "WindowAdapter";
    private String type;
    private AMap map;
    private Marker marker;
    private TaskBean taskBean;
    private LatLng latLng;

    public WindowAdapter(Context context) {
        this.context = context;
    }

    public WindowAdapter(Context context, String type, AMap map, Marker marker, TaskBean taskBean, LatLng latLng) {
        this.context = context;
        this.marker = marker;
        this.type = type;
        this.taskBean = taskBean;
        this.latLng = latLng;
        this.map = map;
        //设置自定义弹窗
        map.setInfoWindowAdapter(this);
        //绑定信息窗点击事件
        map.setOnInfoWindowClickListener(this);
        map.setOnMarkerClickListener(this);
        CommunityBriefBean bean = taskBean.getCommunity();
        String titleStr = "";
        String snippet = "";
        if (null != bean) {
            titleStr = reTextNull(bean.getCommName());
            if (TextUtils.isEmpty(titleStr)) {
                titleStr = reTextNull(taskBean.getGisObj().getName());
            }
            snippet = reTextNull(bean.getCommAddress());
        } else {
            titleStr = reTextNull(taskBean.getGisObj().getName());
        }

        if ("polygon".equals(type)) {
            addMarkerOptions(titleStr, snippet);
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        //关联布局
        View view = LayoutInflater.from(context).inflate(R.layout.layout_info_item, null);
        //标题
        TextView title = view.findViewById(R.id.info_title);
        //地址信息
        TextView address = view.findViewById(R.id.info_adress);
        //小区名称
        TextView name = view.findViewById(R.id.info_name);
        //缺少信息
        TextView defectNumber = view.findViewById(R.id.info_defect_number);

        name.setText(marker.getTitle());
        address.setText(marker.getSnippet());
        String testStr = context.getResources().getString(R.string.house_task_area_defect_number);
        String result = String.format(testStr, "6");
        defectNumber.setText(result);

        return view;
    }

    private void addMarkerOptions(String title, String snippet) {

        MarkerOptions markerOption = new MarkerOptions();
        markerOption.draggable(false).title(title).snippet(snippet).draggable(false).position(latLng);
        marker = map.addMarker(markerOption);
        marker.showInfoWindow();
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
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
        Intent intent = new Intent(context, TaskDetailsInfoActivity.class);
        Bundle bundle = new Bundle();
        CommunityBriefBean communityBriefBean = taskBean.getCommunity();
        String id = "";
        if (null != communityBriefBean && !TextUtils.isEmpty(communityBriefBean.getId())) {
            id = communityBriefBean.getId();
        }
        bundle.putString("communityId", id);
        bundle.putString("gisId", taskBean.getBusiObjId());
        bundle.putString("taskId", taskBean.getId());
        intent.putExtras(bundle);
        context.startActivity(intent);
        Log.e(TAG, "InfoWindow被点击了");
    }
}
