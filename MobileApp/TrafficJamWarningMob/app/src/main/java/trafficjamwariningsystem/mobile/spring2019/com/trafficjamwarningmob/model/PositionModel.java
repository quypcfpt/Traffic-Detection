package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model;

import java.io.Serializable;

public class PositionModel {
    double latitude;
    double longtitude;

    public PositionModel(double latitude, double longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }
}
