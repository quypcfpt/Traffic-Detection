package com.spring2019.mobile.trafficJamWarningSystem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.spring2019.mobile.trafficJamWarningSystem.R;
import com.spring2019.mobile.trafficJamWarningSystem.model.CameraModel;
import com.spring2019.mobile.trafficJamWarningSystem.model.StreetModel;
import com.spring2019.mobile.trafficJamWarningSystem.utils.ItemClickListener;

import java.util.List;

class RecycleViewCameraHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txtName;
    public TextView txtDistrict;
    public ImageView info;
    public RelativeLayout relativeLayout;
    private ItemClickListener itemClickListener;
    public RecycleViewCameraHolder(@NonNull View itemView) {
        super(itemView);
        txtName = (TextView)itemView.findViewById(R.id.name);
        txtDistrict = (TextView) itemView.findViewById(R.id.version_heading);
        info = (ImageView) itemView.findViewById(R.id.item_info);
        relativeLayout=(RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }
    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition());
    }
}
public class CameraAdapter extends RecyclerView.Adapter<RecycleViewCameraHolder>{
    private List<CameraModel> dataSet;
    Context mContext;

    public CameraAdapter(List<CameraModel> dataSet, Context mContext) {
        this.dataSet = dataSet;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public RecycleViewCameraHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item,viewGroup,false);
        return new RecycleViewCameraHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewCameraHolder myViewHolder, int position) {
        final CameraModel models = dataSet.get(position);
        myViewHolder.txtName.setText(models.getDescription());
        myViewHolder.txtDistrict.setVisibility(View.GONE);
        myViewHolder.info.setImageResource(models.getObserved_status()==1?R.mipmap.green:R.mipmap.red);
        myViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(mContext, ""+models.getId(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}