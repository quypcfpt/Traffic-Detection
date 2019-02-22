package com.spring2019.mobile.trafficJamWarningSystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.Snackbar;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spring2019.mobile.trafficJamWarningSystem.R;
import com.spring2019.mobile.trafficJamWarningSystem.model.StreetModel;

import java.util.ArrayList;
import java.util.List;

public class StreetAdapter extends ArrayAdapter<StreetModel>  implements View.OnClickListener{
    private final String Extra_MEss = "com.example.quy.myapplication";
    private List<StreetModel> dataSet;
    Context mContext;
    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView info;
        RelativeLayout relativeLayout;
    }



    public StreetAdapter(List<StreetModel> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;

    }


    @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        StreetModel dataModel=(StreetModel)object;
        String statusString ="";
        if(1==1){
            statusString = "Không Kẹt Xe";
        }else{
            statusString = "Kẹt Xe";
        }
        Snackbar.make(v, "Release date " +statusString, Snackbar.LENGTH_LONG)
                .setAction("No action", null).show();

//                break;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final StreetModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {


            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);
            viewHolder.relativeLayout=(RelativeLayout)convertView.findViewById(R.id.relativeLayout);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getName());
//        viewHolder.txtType.setText(dataModel.getType());
//        viewHolder.txtVersion.setText(dataModel.getVersion_number());
//        viewHolder.info.setImageResource(dataModel.getStatus()==1? R.mipmap.green:R.mipmap.red);
        viewHolder.info.getLayoutParams().height=50;
        viewHolder.info.getLayoutParams().width=50;
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);

        // Return the completed view to render on screen
        return convertView;


    }

}
