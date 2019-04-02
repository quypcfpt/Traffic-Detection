package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.R;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity.CameraInBookmarkActivity;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiClient;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api.ApiInterface;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.BookmarkModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.MultipleBookmarkModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.Response;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.utils.ItemClickListener;

class RecycleViewBookmarkHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ApiInterface apiInterface;
    public TextView txtName;
    public TextView txtDistrict;
    public ImageView info;
    public RelativeLayout relativeLayout;
    private ItemClickListener itemClickListener;
    public RecycleViewBookmarkHolder(@NonNull final View itemView) {
        super(itemView);
        txtName = (TextView) itemView.findViewById(R.id.name);
        txtDistrict = (TextView) itemView.findViewById(R.id.version_heading);
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

public class BookmarkAdapter extends RecyclerView.Adapter<RecycleViewBookmarkHolder> {
    public ApiInterface apiInterface;
    private List<BookmarkModel> dataSet;
    Context mContext;

    public BookmarkAdapter(List<BookmarkModel> dataSet, Context mContext) {
        this.dataSet = dataSet;
        this.mContext = mContext;
    }

    public void updateData(List<BookmarkModel> dataset) {
        dataSet.clear();
        dataSet.addAll(dataset);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecycleViewBookmarkHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item, viewGroup, false);
        return new RecycleViewBookmarkHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecycleViewBookmarkHolder viewHolder, int position) {
        final BookmarkModel models = dataSet.get(position);
        viewHolder.txtName.setText(models.getOrigin() + "-" + models.getDestination());
        viewHolder.txtDistrict.setVisibility(View.GONE);
        viewHolder.info.setImageResource(R.mipmap.cancel);
        viewHolder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doDeleteBookmark(models.getId());
            }
        });
        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(view.getContext(), CameraInBookmarkActivity.class);
                intent.putExtra("ORI", models.getOri_coordinate());
                intent.putExtra("DES", models.getDes_coordinate());
                String header = models.getOrigin() + " - " + models.getDestination();
                intent.putExtra("HEADER", header);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void doDeleteBookmark(int bookmarkId) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Response<String>> responseCall = apiInterface.deleteBookmarkWithId(bookmarkId);
        responseCall.enqueue(new Callback<Response<String>>() {
            @Override
            public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                Response<String> message = response.body();
                String data = message.getData();
                if (data.equals("1")) {
                    Toast.makeText(mContext, "Delete Success", Toast.LENGTH_SHORT).show();
                    reloadData();
                } else {
                    Toast.makeText(mContext, "Delete Failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Response<String>> call, Throwable t) {

            }
        });
    }

    private void reloadData() {
        try {
            String username;
            String password;
            int id;
            String fileName = "accountInfo";
            FileInputStream fileIn = mContext.openFileInput(fileName);
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            char[] inputBuffer = new char[100];
            String s = "";
            int charRead;
            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                // char to string conversion
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s += readstring;
            }
            InputRead.close();
            String[] parts = s.split("-");
            id = Integer.parseInt(parts[0]);
            username = parts[1];
            password = parts[2];
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<Response<List<BookmarkModel>>> responseCall = apiInterface.getBookMakByAccountId(id);
            responseCall.enqueue(new Callback<Response<List<BookmarkModel>>>() {
                @Override
                public void onResponse(Call<Response<List<BookmarkModel>>> call, retrofit2.Response<Response<List<BookmarkModel>>> response) {
                    Response<List<BookmarkModel>> message = response.body();
                    List<BookmarkModel> models = message.getData();
                    updateData(models);
                }

                @Override
                public void onFailure(Call<Response<List<BookmarkModel>>> call, Throwable t) {

                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
