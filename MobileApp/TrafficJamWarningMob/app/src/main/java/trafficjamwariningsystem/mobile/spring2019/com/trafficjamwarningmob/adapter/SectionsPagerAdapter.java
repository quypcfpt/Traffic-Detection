package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity.AccountActivity;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity.SearchRouteActitvity;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity.StreetListActivity;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new StreetListActivity();
            case 1:
                return new SearchRouteActitvity();
            case 2:
                return new AccountActivity();
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Tab1";
            case 1:
                return "Tab2";
            case 2:
                return "Tab3";
        }
        return null;
    }
}
