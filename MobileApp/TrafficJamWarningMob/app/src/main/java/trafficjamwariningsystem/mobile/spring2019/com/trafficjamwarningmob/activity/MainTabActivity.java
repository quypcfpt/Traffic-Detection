package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.messaging.FirebaseMessaging;

import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.R;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.adapter.SectionsPagerAdapter;

public class MainTabActivity extends AppCompatActivity implements View.OnClickListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private LinearLayout buttonToMap;
    static public final String TOPIC="TJWS";
    static public final String[] PERMISSION  = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
    static public final int REQUEST_CODE=1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


          FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);

        setContentView(R.layout.activity_main_tab);
        verifyPermission();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        buttonToMap = (LinearLayout)findViewById(R.id.goToMap);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setBackgroundColor(getColor(R.color.colorPrimary));
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.mipmap.list);
        tabLayout.getTabAt(1).setIcon(R.mipmap.direction);
        tabLayout.getTabAt(2).setIcon(R.mipmap.account);
        buttonToMap.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goToMap:
                Intent intent=new Intent(MainTabActivity.this,MapsActivity.class);
                startActivity(intent);
        }
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_tab, container, false);
            return rootView;
        }
    }

    private void verifyPermission(){
        int permissionCOARSE = ActivityCompat.checkSelfPermission(MainTabActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionFINE = ActivityCompat.checkSelfPermission(MainTabActivity.this,Manifest.permission.ACCESS_FINE_LOCATION);

        if(permissionCOARSE != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainTabActivity.this,PERMISSION,REQUEST_CODE);
        }
        if(permissionFINE!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainTabActivity.this,PERMISSION,REQUEST_CODE);
        }
    }

}