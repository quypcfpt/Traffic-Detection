package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.R;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.BookmarkModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.utils.ItemClickListener;

class RecycleViewBookmarkHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txtName;
    public TextView txtDistrict;
    public ImageView info;
    public RelativeLayout relativeLayout;
    private ItemClickListener itemClickListener;
    public RecycleViewBookmarkHolder(@NonNull View itemView) {
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
public class BookmarkAdapter extends RecyclerView.Adapter<RecycleViewBookmarkHolder> {
    private List<BookmarkModel> dataSet;
    Context mContext;

    public BookmarkAdapter(List<BookmarkModel> dataSet, Context mContext) {
        this.dataSet = dataSet;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public RecycleViewBookmarkHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item,viewGroup,false);
        return new RecycleViewBookmarkHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewBookmarkHolder viewHolder, int position) {
        final BookmarkModel models =dataSet.get(position);
        viewHolder.txtName.setText(models.getOrigin()+"-"+models.getDestination());
        viewHolder.txtDistrict.setVisibility(View.GONE);
        viewHolder.info.setImageResource(R.mipmap.cancel);
        viewHolder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Delete " + models.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
