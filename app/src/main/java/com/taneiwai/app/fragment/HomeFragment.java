package com.taneiwai.app.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.taneiwai.app.R;
import com.taneiwai.app.adapter.ViewPageFragmentAdapter;
import com.taneiwai.app.base.BaseFragment;
import com.taneiwai.app.widget.EmptyLayout;
import com.taneiwai.app.widget.PagerSlidingTabStrip;

import butterknife.Bind;

/**
 * Created by mac on 15/12/7.
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.pager_tabstrip)
    PagerSlidingTabStrip mTabStrip;
    @Bind(R.id.pager)
    ViewPager mViewPager;
    @Bind(R.id.error_layout)
    EmptyLayout mErrorLayout;
    @Bind(R.id.class_add)
    ImageView mAddIv;
    @Bind(R.id.class_setting)
    ImageView mSettingIv;
    private ViewPageFragmentAdapter mTabsAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.layout_fragment_home;
    }

    @Override
    public void initView(View view) {

        mAddIv.setOnClickListener(this);
        mSettingIv.setOnClickListener(this);
        mTabsAdapter = new ViewPageFragmentAdapter(getChildFragmentManager(), mTabStrip, mViewPager);
        setScreenPageLimit();
        onSetupTabAdapter(mTabsAdapter);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    private void setScreenPageLimit() {

    }

    /*
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
        if (outState != null && mViewPager != null) {
            outState.putInt("position", mViewPager.getCurrentItem());
        }
        //super.onSaveInstanceState(outState);
    }
    */

    private void onSetupTabAdapter(ViewPageFragmentAdapter adapter){
        String[] title = getResources().getStringArray(R.array.news_viewpage_arrays);

        adapter.addTab(title[0], "class_1_1", SubclassFragment.class, getBundle(11));
        adapter.addTab(title[1], "class_1_2", SubclassFragment.class, getBundle(12));
        adapter.addTab(title[2], "class_2_1", SubclassFragment.class, getBundle(21));
        adapter.addTab(title[3], "class_2_2", SubclassFragment.class, getBundle(22));
    }

    private Bundle getBundle(int classType) {
        Bundle bundle = new Bundle();
        bundle.putInt(CLASS_ITEM, classType);
        return bundle;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

    }
}
