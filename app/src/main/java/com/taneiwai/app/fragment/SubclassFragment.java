package com.taneiwai.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.taneiwai.app.R;
import com.taneiwai.app.base.BaseFragment;
import com.taneiwai.app.widget.EmptyLayout;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by weiTeng on 15/12/12.
 */
public class SubclassFragment extends BaseFragment {

    private static final String TAG = "SubclassFragment";

    @Bind(R.id.class_listview)
    ListView mListView;
    @Bind(R.id.empty_layout)
    EmptyLayout mEmptyLayout;

    private ArrayList<Class> mClasses;

    @Override
    public int getLayoutId() {
        return R.layout.layout_fragment_classes;
    }

    @Override
    public void initView(View view) {
        Bundle bundle = getArguments();
        int classType = bundle.getInt(CLASS_ITEM);

        if(mClasses == null){
            mClasses = new ArrayList<>();
        }

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

}
