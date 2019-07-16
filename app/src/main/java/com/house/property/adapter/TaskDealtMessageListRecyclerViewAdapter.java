package com.house.property.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.house.property.R;
import com.house.property.model.main.entity.TaskBean;

import java.util.HashMap;
import java.util.List;

import static com.house.property.base.Constants.TASK_NUMBER;

public class TaskDealtMessageListRecyclerViewAdapter extends RecyclerView.Adapter<TaskDealtMessageListRecyclerViewAdapter.ViewHolder> {
    private List<TaskBean> mlist;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public static interface OnItemClickListener {
        void onItemClick(int position);
        void onItemLongClick(int position);
        void onItemUpdateClick(int position);
    }

    public TaskDealtMessageListRecyclerViewAdapter(Context context, List<TaskBean> list, OnItemClickListener onItemClickListener) {
        super();
        mlist = list;
        this.context = context;
        mOnItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView message;
        public TextView time;
        public LinearLayout ll;
        public LinearLayout submitll;
        public ViewHolder(View v) {
            super(v);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TaskDealtMessageListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                 int viewType) {
        // create a new view
        View convertView = LayoutInflater.from(context)
                .inflate(R.layout.listview_task_dealt_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.title = convertView.findViewById(R.id.title);
        viewHolder.message = convertView.findViewById(R.id.message);
        viewHolder.time = convertView.findViewById(R.id.time);
        viewHolder.ll = convertView.findViewById(R.id.ll);
        viewHolder.submitll = convertView.findViewById(R.id.submit_ll);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TaskBean map = mlist.get(position);
        holder.title.setText(map.getId());
        String address = "";
        if (null != map.getCommunity()) {
            if (TextUtils.isEmpty(map.getCommunity().getCommAddress())) {
                address = map.getGisObj().getName();
            } else {
                address = map.getCommunity().getCommAddress();
            }
        }else{
            address = map.getGisObj().getName();
        }
        holder.message.setText(address);
        holder.time.setText(map.getCreateTime());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(position);
            }
        });
        holder.ll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemClickListener.onItemLongClick(position);
                return true;
            }
        });

        holder.submitll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemUpdateClick(position);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public void refreshData(List<TaskBean> list) {
        if (null != mlist) {
            mlist.clear();
        }
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    public void deleteImte(int postion){
       mlist.remove(postion);
//        notifyItemRemoved(postion);
        notifyDataSetChanged();
        TASK_NUMBER = String.valueOf(mlist.size());
    }


    public List<TaskBean> getAllList() {
        return mlist;
    }

}
