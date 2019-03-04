package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class MultiStreetModel {

    @Expose
    List<StreetModel> streetList;
    @Expose
    int totalPage;
    @Expose
    long totalRecord;
    @Expose
    int currentPage;

    public MultiStreetModel() {
    }

    public List<StreetModel> getStreetList() {
        return streetList;
    }

    public void setStreetList(List<StreetModel> streetList) {
        this.streetList = streetList;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
