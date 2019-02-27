package com.spring2019.mobile.trafficJamWarningSystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.Snackbar;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.spring2019.mobile.trafficJamWarningSystem.R;
import com.spring2019.mobile.trafficJamWarningSystem.activity.ListCameraActivity;
import com.spring2019.mobile.trafficJamWarningSystem.activity.MainActivity;
import com.spring2019.mobile.trafficJamWarningSystem.model.CameraModel;
import com.spring2019.mobile.trafficJamWarningSystem.model.StreetModel;
import com.spring2019.mobile.trafficJamWarningSystem.utils.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

 class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
     public TextView txtName;
     public TextView txtDistrict;
     public ImageView info;
     public RelativeLayout relativeLayout;
     private ItemClickListener itemClickListener;
    public RecycleViewHolder(@NonNull View itemView) {
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
public class StreetAdapter extends RecyclerView.Adapter<RecycleViewHolder>{
    private List<StreetModel> dataSet;
    Context mContext;

    public StreetAdapter(List<StreetModel> dataSet, Context mContext) {
        this.dataSet = dataSet;
        this.mContext = mContext;
    }



    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item,viewGroup,false);

        return new RecycleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecycleViewHolder myViewHolder, int position) {
        final StreetModel models = dataSet.get(position);
        myViewHolder.txtName.setText(models.getName());
        myViewHolder.txtDistrict.setText(models.getDistrict());
        myViewHolder.info.setVisibility(View.GONE);
        myViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Context context = view.getContext();
                Toast.makeText(mContext, ""+models.getId(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,ListCameraActivity.class);
                intent.putExtra("STREET_NAME",models.getName());
                intent.putExtra("STREET_ID",models.getId()+"");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }



}
