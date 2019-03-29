package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model;

import com.google.gson.annotations.Expose;

public class CameraModel {
    @Expose
    private int id;

    @Expose
    private String description;

    @Expose
    private String position;

    @Expose
    private String resource;

    @Expose
    private int observerStatus;

    @Expose
    private int camOrder;

    @Expose
    private StreetModel street;
    @Expose
    private String imgUrl;
    @Expose
    private String time;

    public CameraModel(int id, String description, String position, String resource, int observerStatus, int camOrder, StreetModel street, String imgUrl, String time) {
        this.id = id;
        this.description = description;
        this.position = position;
        this.resource = resource;
        this.observerStatus = observerStatus;
        this.camOrder = camOrder;
        this.street = street;
        this.imgUrl = imgUrl;
        this.time = time;
    }

    public CameraModel(int id, String description, String position, String resource, int observerStatus, int camOrder, StreetModel street, String imgUrl, String time, float distance) {
        this.id = id;
        this.description = description;
        this.position = position;
        this.resource = resource;
        this.observerStatus = observerStatus;
        this.camOrder = camOrder;
        this.street = street;
        this.imgUrl = imgUrl;
        this.time = time;
        this.distance = distance;
    }

    private float distance;

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CameraModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public int getObserverStatus() {
        return observerStatus;
    }

    public void setObserverStatus(int observerStatus) {
        this.observerStatus = observerStatus;
    }

    public int getCamOrder() {
        return camOrder;
    }

    public void setCamOrder(int camOrder) {
        this.camOrder = camOrder;
    }

    public StreetModel getStreet() {
        return street;
    }

    public void setStreet(StreetModel street) {
        this.street = street;
    }


}
