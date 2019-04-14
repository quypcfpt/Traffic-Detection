package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.R;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity.ImageShowActivity;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.customeview.SquareImageView;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.utils.ItemClickListener;

class RecycleViewImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public  SquareImageView siv;
    private ItemClickListener itemClickListener;
    public RecycleViewImageHolder(@NonNull View itemView) {
        super(itemView);
        siv = (SquareImageView) itemView.findViewById(R.id.squareimg);
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
public class ImageAdapter extends RecyclerView.Adapter<RecycleViewImageHolder> {
    private List<String> imgList;

    private Context c;

    public ImageAdapter(Context c, List imgList) {
        this.c = c;
        this.imgList = imgList;
    }

    @NonNull
    @Override
    public RecycleViewImageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid, viewGroup, false);
        return new RecycleViewImageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewImageHolder recycleViewImageHolder, int i) {
        final String path = imgList.get(i);
        if(path.equals("")){
            Picasso.get()
                    .load(R.mipmap.image)
                    .resize(250, 250)
                    .placeholder(R.mipmap.image)
                    .centerCrop()
                    .into(recycleViewImageHolder.siv);
        }else {
            Picasso.get()
                    .load(path)
                    .placeholder(R.mipmap.image)
                    .resize(250, 250)
                    .centerCrop()
                    .into(recycleViewImageHolder.siv);
        }
        recycleViewImageHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(c.getApplicationContext(),ImageShowActivity.class);
                if(imgList.get(position).equals("")){
                    Toast.makeText(c, "Chưa có hình anh để thể hiện . Xin đợi hệ thống cập nhật trong ít phút", Toast.LENGTH_SHORT).show();
                }else {
                    intent.putExtra("IMG_LINK", imgList.get(position));
                }
                c.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return imgList.size();
    }

    public List<String> getDataSet() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }
}
