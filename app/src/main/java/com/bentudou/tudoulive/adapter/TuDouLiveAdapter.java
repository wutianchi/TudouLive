package com.bentudou.tudoulive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bentudou.tudoulive.R;
import com.bentudou.tudoulive.config.Constant;
import com.bentudou.tudoulive.model.LiveData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 *Created by lzz on 2016/3/2.
 * 成长记录适配器
 */
public class TuDouLiveAdapter extends BaseAdapter {
    List<LiveData> list;
    Context context;
    public TuDouLiveAdapter(List<LiveData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (list==null) {
            return 0;
        } else {
            return list.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (list==null) {
            return null;
        } else {
            return list.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ClassHolder classHolder;
        if(convertView==null){
            classHolder = new ClassHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_live,null);
            classHolder.iv_live = (ImageView) convertView.findViewById(R.id.iv_live);
            classHolder.tv_camera_code = (TextView)convertView.findViewById(R.id.tv_camera_code);
            classHolder.tv_camera_coverage = (TextView)convertView.findViewById(R.id.tv_camera_coverage);
            convertView.setTag(classHolder);
        }else {
            classHolder = (ClassHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(Constant.URL_BASE_IMG+list.get(position).getCameraIcon(),classHolder.iv_live);
        classHolder.tv_camera_code.setText(list.get(position).getCameraCode()+"号 "+list.get(position).getCameraCoverage());
        classHolder.tv_camera_coverage.setText(list.get(position).getWarehouseName());
        return convertView;
    }
    class ClassHolder{
        ImageView iv_live;
        TextView tv_camera_code,tv_camera_coverage;
    }

}
