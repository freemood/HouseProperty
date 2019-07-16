package com.house.property.model.task.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.house.property.R;
import com.house.property.base.BaseFragmentActivity;
import com.house.property.base.BasePagerAdapter;
import com.house.property.model.task.fragment.TaskDealtMessageFragment;
import com.house.property.model.task.fragment.TaskDoneMessageFragment;
import com.house.property.model.task.fragment.TaskThroughMessageFragment;
import com.house.property.view.CustomViewPager;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 待办任务列表
 */
public class TaskListActivity extends BaseFragmentActivity {
    @BindView(R.id.vPager)
    CustomViewPager viewPager;
    @BindView(R.id.page_title)
    TextView titleTv;

    @BindView(R.id.iv_tab_bottom_img3)
    ImageView bottomImg3;
    @BindView(R.id.iv_tab_bottom_img2)
    ImageView bottomImg2;
    @BindView(R.id.iv_tab_bottom_img1)
    ImageView bottomImg1;

    private BasePagerAdapter mPagerAdapter;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private SoftReference<Fragment> mTaskDealtMessageFragment = null;
    private SoftReference<Fragment> mTaskDoneMessageFragment = null;
    private SoftReference<Fragment> mTaskThroughMessageFragment = null;
    private FragmentManager mFragmentManager = null;
    private Fragment currentFragment = null;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_task_list;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mTaskDealtMessageFragment = new SoftReference(new TaskDealtMessageFragment());
        mTaskDoneMessageFragment = new SoftReference(new TaskDoneMessageFragment());
        mTaskThroughMessageFragment = new SoftReference(new TaskThroughMessageFragment());
        mFragmentList.add(mTaskDealtMessageFragment.get());
        mFragmentList.add(mTaskDoneMessageFragment.get());
        mFragmentList.add(mTaskThroughMessageFragment.get());
        mFragmentManager = getSupportFragmentManager();

        //viewPager = findViewById(R.id.view_pager);
        mPagerAdapter = new BasePagerAdapter(mFragmentManager, mFragmentList);
        //设置Adapter
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setScanScroll(false);
        viewPager.setCurrentItem(0);
        currentFragment = mFragmentList.get(0);
        titleTv.setText(R.string.house_task_dealt);
        bottomImg1.setVisibility(View.VISIBLE);
        bottomImg2.setVisibility(View.GONE);
        bottomImg3.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.house_task_dealt_ll, R.id.house_task_done_ll, R.id.house_task_through_ll, R.id.page_back, R.id.page_right_rl})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.house_task_dealt_ll:
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragmentList.get(0));
                viewPager.setCurrentItem(0);
                titleTv.setText(R.string.house_task_dealt);
                bottomImg1.setVisibility(View.VISIBLE);
                bottomImg2.setVisibility(View.GONE);
                bottomImg3.setVisibility(View.GONE);
                break;
            case R.id.house_task_done_ll:
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragmentList.get(1));
                viewPager.setCurrentItem(1);
                titleTv.setText(R.string.house_task_done);
                bottomImg1.setVisibility(View.GONE);
                bottomImg2.setVisibility(View.VISIBLE);
                bottomImg3.setVisibility(View.GONE);
                break;
            case R.id.house_task_through_ll:
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragmentList.get(1));
                viewPager.setCurrentItem(2);
                titleTv.setText(R.string.house_task_through);
                bottomImg1.setVisibility(View.GONE);
                bottomImg2.setVisibility(View.GONE);
                bottomImg3.setVisibility(View.VISIBLE);
                break;
            case R.id.page_right_rl:

                break;
            case R.id.page_back:
                finish();
                break;
        }
    }

}
