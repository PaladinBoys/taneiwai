package com.taneiwai.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taneiwai.app.R;
import com.taneiwai.app.model.ClassInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by weiTeng on 15/12/19.
 */
public class SubclassAdapter extends BaseAdapter{

    private Context mContext;
    private List<ClassInfo> mClassInfos;

    public SubclassAdapter(Context context, List<ClassInfo> classInfos){
        this.mContext = context;
        this.mClassInfos = classInfos;
    }

    @Override
    public int getCount() {
        return mClassInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mClassInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.item_subclass_layout, null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        ClassInfo classInfo = mClassInfos.get(position);
        holder.typeTv.setText(classInfo.typeName);
        holder.timeTv.setText(classInfo.time);
        if(classInfo.type == 1){
            holder.typeIv.setImageResource(R.drawable.exercise_icon);
        }else if(classInfo.type == 2){
            holder.typeIv.setImageResource(R.drawable.notice_school);
        }else {
            holder.typeIv.setImageResource(R.drawable.exercise_icon);
        }

        if(classInfo.type == 1){

        }
        return null;
    }

    static class ViewHolder{

        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }

        @Bind(R.id.item_rl)
        RelativeLayout contentRl;
        @Bind(R.id.type_icon)
        ImageView typeIv;
        @Bind(R.id.type_tv)
        TextView typeTv;
        @Bind(R.id.time_tv)
        TextView timeTv;
        @Bind(R.id.subject_content)
        LinearLayout subLinear;
        @Bind(R.id.line)
        View line;
        @Bind(R.id.buttom_ll)
        RelativeLayout buttomRl;
        @Bind(R.id.btn_iv)
        ImageView btnIv;
        @Bind(R.id.watch_tv)
        TextView watchTv;
        @Bind(R.id.right_tv)
        TextView rightTv;
    }
}
