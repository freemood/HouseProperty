package com.house.property.model.task.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.house.property.R;
import com.house.property.adapter.TaskDoneMessageListRecyclerViewAdapter;
import com.house.property.base.BaseFragment;
import com.house.property.model.main.entity.TaskBean;
import com.house.property.model.main.entity.TaskListBean;
import com.house.property.model.main.iface.ITaskListView;
import com.house.property.model.main.presenter.TaskListPresenter;
import com.house.property.utils.SharedPreferencesUtil;
import com.house.property.utils.ToastUtils;
import com.house.property.view.IBaseView;
import com.house.property.widget.RLoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

import static com.house.property.base.Constants.TAKE_TYPE_DEALT;
import static com.house.property.base.Constants.TAKE_TYPE_DONE;

/**
 * 已办任务
 */
public class TaskDoneMessageFragment extends BaseFragment implements ITaskListView {
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    private RLoadingDialog mLoadingDialog;
    private TaskDoneMessageListRecyclerViewAdapter adapter;
    private List<TaskBean> mlist = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private String userName;
    private TaskListPresenter taskListPresenter = new TaskListPresenter(this, this);
    private TaskDoneMessageListRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new TaskDoneMessageListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
            //dapter.notifyDataSetChanged();
        }

        @Override
        public void onItemLongClick(int postion) {

        }
    };

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_task_done_list, null);
    }

    @Override
    protected void initBundleData() {
        userName = (String) SharedPreferencesUtil.getData("name", "");
        taskListPresenter.getTaskList(userName, TAKE_TYPE_DONE);
    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        initAdapter();
    }

    @Override
    protected void initData() {
        presentersList.add(taskListPresenter);
    }

    private void initAdapter() {

        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        adapter = new TaskDoneMessageListRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showResult(TaskListBean bean) {
        if (null == bean) {
            return;
        }
        mlist = bean.getList();
        adapter.refreshData(mlist);
    }

    @Override
    public void showLoading() {
//        mLoadingDialog.show();
    }

    @Override
    public void closeLoading() {
//        mLoadingDialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(mContext, msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
