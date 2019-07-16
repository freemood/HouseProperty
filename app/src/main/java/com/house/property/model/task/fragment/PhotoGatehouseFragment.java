package com.house.property.model.task.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import com.house.property.R;
import com.house.property.adapter.PhotoBaseRecyclerViewAdapter;
import com.house.property.adapter.GridSpacingItemDecoration;
import com.house.property.adapter.MyGridLayoutManager;
import com.house.property.base.BaseFragment;
import com.house.property.model.task.entity.PhotoBean;
import com.house.property.utils.PhotoUtils;
import com.house.property.utils.ToastUtils;
import com.house.property.view.IBaseView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.house.property.base.Constants.TAKE_PHOTO;


/**
 * 拍照
 */
public class PhotoGatehouseFragment extends BaseFragment implements IBaseView {

    //private RLoadingDialog mLoadingDialog;
    private PhotoBaseRecyclerViewAdapter adapter;
    private List<PhotoBean> mlist = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;

    private PhotoBaseRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new PhotoBaseRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {

        }

        @Override
        public void onItemLongClick(int postion) {

        }
    };

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_more_list, null);
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        //mLoadingDialog = new RLoadingDialog(mContext, true);
        initAdapter();

    }

    @OnClick({R.id.add_saomiao})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_saomiao:
                PhotoUtils.takePicture(getActivity(), TAKE_PHOTO);
                break;
        }
    }

    @Override
    protected void initData() {

    }

    private void initAdapter() {

        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new MyGridLayoutManager(mContext, 4);
        //mLayoutManager.setScrollEnabled(false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(mContext, 4, 20));
        adapter = new PhotoBaseRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
        mRecyclerView.setAdapter(adapter);


    }

    public void setPhoteBean(PhotoBean photoBean) {
        adapter.addBtn(photoBean);
    }

    @Override
    public void showLoading() {
        // mLoadingDialog.show();
    }

    @Override
    public void closeLoading() {
        //mLoadingDialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(mContext, msg);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

