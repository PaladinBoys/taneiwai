package com.taneiwai.app.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.taneiwai.app.R;
import com.taneiwai.app.base.BaseFragment;
import com.taneiwai.app.fragment.CommunionFragment;
import com.taneiwai.app.fragment.HomeFragment;
import com.taneiwai.app.fragment.MineFragment;
import com.taneiwai.app.widget.TabView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TabView.OnTabClickListener{

    @Bind(R.id.tabview)
    TabView mTabview;

    private ArrayList<BaseFragment> mFragments;
    private FragmentManager mFragmentManager;
    private BaseFragment mCurrentFragment;
    private int mIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initAllFragment();
    }

    private void initAllFragment() {
        mIndex = 0;
        if(mFragments == null){
            mFragments = new ArrayList<>();
        }
        mFragments.add(new HomeFragment());
        mFragments.add(new CommunionFragment());
        mFragments.add(new MineFragment());
        mCurrentFragment = mFragments.get(mIndex);
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.contentPanel, mCurrentFragment).commit();
    }

    private void initView(){

        mTabview.setDrawableWidth(56);
        mTabview.setNormalDrawables(
                R.drawable.home_icon_head_normal,
                R.drawable.home_icon_com_normal,
                R.drawable.home_icon_mine_normal
        );
        mTabview.setSelectedDawables(
                R.drawable.home_icon_head_selected,
                R.drawable.home_icon_com_selected,
                R.drawable.home_icon_mine_selected
        );
        mTabview.setOnTabClickListener(this);
    }

    @Override
    public void onTabClick(int index) {
        mIndex = index;
        switchFragment(mFragments.get(mIndex));
    }

    private void switchFragment(BaseFragment nextFragment){
        if(mCurrentFragment != nextFragment){
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            if(!nextFragment.isAdded()){
                transaction.hide(mCurrentFragment).add(R.id.contentPanel, nextFragment).show(nextFragment).commit();
            }else{
                transaction.hide(mCurrentFragment).show(nextFragment).commit();
            }
            mCurrentFragment = nextFragment;
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
