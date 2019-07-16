package com.house.property.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.house.property.R;
import com.house.property.model.task.entity.PhotoBean;
import com.house.property.utils.CornerTransform;

import java.io.File;
import java.util.List;

public class PhotoBaseRecyclerViewAdapter extends RecyclerView.Adapter<PhotoBaseRecyclerViewAdapter.ViewHolder> {
    private List<PhotoBean> mlist;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public static interface OnItemClickListener {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }

    public PhotoBaseRecyclerViewAdapter(Context context, List<PhotoBean> list, OnItemClickListener onItemClickListener) {
        super();
        mlist = list;
        this.context = context;
        mOnItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView photo;
        public LinearLayout ll;

        public ViewHolder(View v) {
            super(v);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PhotoBaseRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                      int viewType) {
        // create a new view
        View convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.photo = convertView.findViewById(R.id.photo_preview);
        viewHolder.ll = convertView.findViewById(R.id.photo_ll);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        PhotoBean photoBean = mlist.get(position);
        String path = photoBean.getPath();
        final ImageView photoTv = holder.photo;

        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (file.exists()) {
                Glide.with(context).
                        load(file).
                        apply(new RequestOptions().
                                diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).
                                transform(new CornerTransform(context, 15))).
                        into(photoTv);
            }


        }
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
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public void refreshData(List<PhotoBean> list) {
        if (null != mlist) {
            mlist.clear();
        }
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        if (null != mlist) {
            mlist.clear();
        }
        notifyDataSetChanged();
    }

    public List<PhotoBean> getAllList() {
        return mlist;
    }

    // 添加数据
    public void addData(PhotoBean map, int position) {
        mlist.add(map);
        //添加动画
        notifyItemInserted(position);
    }

    // 删除数据
    public void removeData(int position) {
        mlist.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void addBtn(PhotoBean map) {
        mlist.add(map);
        int count = mlist.size();
        notifyItemInserted(count);
    }
}
