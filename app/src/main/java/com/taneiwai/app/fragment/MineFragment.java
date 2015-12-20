package com.taneiwai.app.fragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taneiwai.app.R;
import com.taneiwai.app.base.BaseFragment;

import java.util.ArrayList;


public class MineFragment extends BaseFragment {
    private ViewPager view_pager;
    ArrayList<View> viewContainter = new ArrayList<View>();
    @Override
    public int getLayoutId() {
        return R.layout.layout_fragment_mine;
    }

    @Override
    public void initView(View view) {
        View view1 = LayoutInflater.from(mActivity).inflate(R.layout.my_tab1, null);
        View view2 = LayoutInflater.from(mActivity).inflate(R.layout.my_tab2, null);
        View view3 = LayoutInflater.from(mActivity).inflate(R.layout.my_tab3, null);
        viewContainter.add(view1);
        viewContainter.add(view2);
        viewContainter.add(view3);

        view_pager= (ViewPager) view.findViewById(R.id.view_pager);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
    view_pager.setAdapter(new PagerAdapter() {
        @Override
        public int getCount() {
            return viewContainter.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(viewContainter.get(position));
            return viewContainter.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            ((ViewPager) container).removeView(viewContainter.get(position));
        }
    });
    }
}
