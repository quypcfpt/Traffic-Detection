package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.R;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity.ImageActivity;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.CameraModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.utils.ItemClickListener;

class RecycleViewCameraHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtName;
    public TextView txtDistrict;
    public TextView txtDistance;
    public ImageView info;
    public RelativeLayout relativeLayout;
    private ItemClickListener itemClickListener;

    public RecycleViewCameraHolder(@NonNull View itemView) {
        super(itemView);
        txtName = (TextView) itemView.findViewById(R.id.name);
        txtDistrict = (TextView) itemView.findViewById(R.id.version_heading);
        txtDistance=(TextView)itemView.findViewById(R.id.distance);
        info = (ImageView) itemView.findViewById(R.id.item_info);
        relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition());
    }
}

public class CameraAdapter extends RecyclerView.Adapter<RecycleViewCameraHolder> {
    private List<CameraModel> dataSet;
    Context mContext;

    public CameraAdapter(List<CameraModel> dataSet, Context mContext) {
        this.dataSet = dataSet;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecycleViewCameraHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item, viewGroup, false);
        return new RecycleViewCameraHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewCameraHolder myViewHolder, int position) {
        final CameraModel models = dataSet.get(position);
        myViewHolder.txtName.setEllipsize(TextUtils.TruncateAt.END);
        myViewHolder.txtName.setMaxWidth(200);
        myViewHolder.txtName.setMaxLines(1);
        myViewHolder.txtName.setText(models.getDescription());
        if(models.getDistance()!= 0){
            myViewHolder.txtDistance.setVisibility(View.VISIBLE);
            myViewHolder.txtDistance.setText(models.getDistance()+"");
        }else{
            myViewHolder.txtDistance.setVisibility(View.GONE);
        }
        myViewHolder.txtDistrict.setVisibility(View.GONE);
        myViewHolder.info.setImageResource(models.getObserverStatus() == 0 ? R.mipmap.green : models.getObserverStatus()==1 ? R.mipmap.red : R.mipmap.yellow );
        myViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Context context = view.getContext();
                Toast.makeText(mContext, "" + models.getId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ImageActivity.class);
                intent.putExtra("CAMERA_ID", models.getId() + "");
                intent.putExtra("CAMERA_STATUS", models.getObserverStatus() + "");
                intent.putExtra("CAMERA_NAME", models.getDescription() + "");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}