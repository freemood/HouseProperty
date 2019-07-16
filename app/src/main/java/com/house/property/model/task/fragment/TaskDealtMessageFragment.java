package com.house.property.model.task.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.amap.api.maps.model.LatLng;
import com.house.property.R;
import com.house.property.adapter.TaskDealtMessageListRecyclerViewAdapter;
import com.house.property.base.BaseFragment;
import com.house.property.model.main.entity.TaskBean;
import com.house.property.model.main.entity.TaskListBean;
import com.house.property.model.main.entity.TaskUpdateEventBean;
import com.house.property.model.main.iface.ITaskListView;
import com.house.property.model.main.iface.ITaskUpdateView;
import com.house.property.model.main.presenter.TaskListPresenter;
import com.house.property.model.main.presenter.TaskUpdatePresenter;
import com.house.property.model.task.entity.GisObjVO;
import com.house.property.utils.SharedPreferencesUtil;
import com.house.property.utils.ToastUtils;
import com.house.property.widget.RLoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.house.property.base.Constants.TAKE_TYPE_DEALT;
import static com.house.property.base.Constants.TASK_NUMBER;

/**
 * 待办任务
 */
public class TaskDealtMessageFragment extends BaseFragment implements ITaskUpdateView, ITaskListView {

    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    private RLoadingDialog mLoadingDialog;
    private TaskDealtMessageListRecyclerViewAdapter adapter;
    private List<TaskBean> mlist = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private String userName;
    private String submitTaskId;
    private int submitPostion = 0;
    private TaskListPresenter taskListPresenter = new TaskListPresenter(this, this);
    private TaskUpdatePresenter taskUpdatePresenter = new TaskUpdatePresenter(this, this);

    private TaskDealtMessageListRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new TaskDealtMessageListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
            if (null == mlist || 0 == mlist.size()) {
                return;
            }
            GisObjVO gisObjBriefBean = mlist.get(postion).getGisObj();
            if (null == gisObjBriefBean) {
                return;
            }
            LatLng latLng = new LatLng(Double.parseDouble(gisObjBriefBean.getCenterY()), Double.parseDouble(gisObjBriefBean.getCenterX()));
            EventBus.getDefault().postSticky(latLng);
            getActivity().finish();
        }

        @Override
        public void onItemLongClick(int postion) {

        }

        @Override
        public void onItemUpdateClick(int position) {
            if (null == mlist || 0 == mlist.size()) {
                return;
            }
            submitPostion = position;
            TaskBean taskBean = mlist.get(position);
            submitTaskId = taskBean.getId();
            showShengheDialog(submitTaskId);
        }
    };

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_task_dealt_list, null);
    }

    @Override
    protected void initBundleData() {
        presentersList.add(taskListPresenter);
        presentersList.add(taskUpdatePresenter);
    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        initAdapter();

    }

    @Override
    protected void initData() {
        userName = (String) SharedPreferencesUtil.getData("name", "");
        taskListPresenter.getTaskList(userName, TAKE_TYPE_DEALT);
    }

    private void initAdapter() {
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        adapter = new TaskDealtMessageListRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
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

    private void showShengheDialog(String taskId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示");
        builder.setMessage("是否提交审核？");
        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateTask(taskId);
            }
        });
        builder.setCancelable(false);
        builder.show();

    }

    private void updateTask(String taskId) {
        taskUpdatePresenter.updateTask(taskId);
    }


    @Override
    public void showResult(TaskUpdateEventBean bean) {
        if (null == bean) {
            return;
        }
        ToastUtils.showToast(mContext, bean.getResult());
        adapter.deleteImte(submitPostion);

    }

    @Override
    public void showLoading() {
        mLoadingDialog.show();
    }

    @Override
    public void closeLoading() {
        mLoadingDialog.dismiss();
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
