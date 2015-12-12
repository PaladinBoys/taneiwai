package com.taneiwai.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.taneiwai.app.R;
import com.taneiwai.app.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by weiTeng on 15/12/12.
 */
public class SubclassFragment extends BaseFragment {


    @Bind(R.id.textview)
    TextView mTextview;

    @Override
    public int getLayoutId() {
        return R.layout.layout_fragment_classes;
    }

    @Override
    public void initView(View view) {
        Bundle bundle = getArguments();
        int classType = bundle.getInt(CLASS_ITEM);
        mTextview.setText(String.valueOf(classType));
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

}
