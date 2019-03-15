package com.spring2019.trafficJamDetectionSystem.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class MultiAccountModel {

    @Expose
    List<AccountModel> accountList;
    @Expose
    int totalPage;
    @Expose
    long totalRecord;
    @Expose
    int currentPage;

    public MultiAccountModel() {
    }

    public List<AccountModel> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<AccountModel> accountList) {
        this.accountList = accountList;
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