package com.spring2019.trafficJamDetectionSystem.common;

public class CoreConstant {

    public static final String API_CAMERA = "/api/camera";
    public static final String API_STREET = "/api/street";
    public static final String API_DETECTION = "/api/detection";
    public static final String API_IMAGE ="/api/images";
    public static final String API_ACCOUNT="/api/account";
    public static final String API_ROLE="/api/role";
    //API Response code
    public static final int STATUS_CODE_SERVER_ERROR = -1;
    public static final int STATUS_CODE_FAIL = 0;
    public static final int STATUS_CODE_SUCCESS = 1;
    public static final int STATUS_CODE_NO_RESULT = 2;

    public static final String MESSAGE_SUCCESS = "Success!";
    public static final String MESSAGE_FAIL = "Fail!";
    public static final String MESSAGE_SERVER_ERROR = "Server error!";
    public static final String MESSAGE_NO_RESULT = "No result!";

    public static final int STATUS_CAMERA_CLEAR=0;
    public static final int STATUS_CAMERA_JAM=1;

    public static final String FIREBASE_TOPIC = "Traffic_Jam_Notification";
}
