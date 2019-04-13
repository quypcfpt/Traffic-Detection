package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.R;

public class ImageShowActivity extends AppCompatActivity implements View.OnClickListener {
private ImageView showImage;
private TextView returnText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);
         showImage = (ImageView) findViewById(R.id.imageShow);
        returnText = (TextView)findViewById(R.id.textReturn);
        returnText.setOnClickListener(this);
        String img_link = getIntent().getStringExtra("IMG_LINK");
        Picasso.get()
                .load(img_link)
                .resize(350,400)
                .centerCrop()
                .into(showImage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textReturn:
                ImageShowActivity.this.finish();
                break;
        }
    }
}
