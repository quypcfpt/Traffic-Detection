package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.R;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity.ImageActivity;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.CameraModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.utils.ItemClickListener;

public class CameraInBookmarkAdapter extends RecyclerView.Adapter<RecycleViewCameraHolder> {
    private List<CameraModel> dataSet;
    Context mContext;

    public CameraInBookmarkAdapter(List<CameraModel> dataSet, Context mContext) {
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
        myViewHolder.txtName.setMaxWidth(750);
        myViewHolder.txtName.setMaxLines(1);
        myViewHolder.txtName.setText(models.getDescription() + " - " + models.getStreet().getName());
        myViewHolder.txtDistrict.setVisibility(View.GONE);
        myViewHolder.info.setImageResource(models.getObserverStatus() == 0 ? R.mipmap.green : models.getObserverStatus()==1 ? R.mipmap.red : R.mipmap.yellow );
        myViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Context context = view.getContext();
                Toast.makeText(mContext, "" + models.getId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ImageActivity.class);
                Bundle bundle = new Bundle();
                String listCamJsonObj = new Gson().toJson(models);
                bundle.putString("CAMINFO", listCamJsonObj);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public List<CameraModel> getDataSet() {
        return dataSet;
    }
}