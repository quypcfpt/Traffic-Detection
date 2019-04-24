package com.spring2019.trafficJamDetectionSystem.schedule;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import com.spring2019.trafficJamDetectionSystem.controllerImpl.DetectionControllerImpl;
import com.spring2019.trafficJamDetectionSystem.entity.Account;
import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.entity.Report;
import com.spring2019.trafficJamDetectionSystem.entity.Street;
import com.spring2019.trafficJamDetectionSystem.model.DetectionModel;
import com.spring2019.trafficJamDetectionSystem.model.ReportModel;
import com.spring2019.trafficJamDetectionSystem.service.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.swing.text.StringContent;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Configuration
@EnableScheduling
public class Scheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);
    private static HashMap<Integer, DetectionModel> detectionModelMap;

    @Autowired
    CameraService cameraService;

    @Autowired
    BookmarkService bookmarkService;

    @Autowired
    ReportService reportService;

    @Autowired
    AndroidPushNotificationsService androidPushNotificationsService;

    @Scheduled(fixedDelay = 10 * 1000)
    public void scheduleFixedDelayTask() {

        if (DetectionControllerImpl.detectResultData == null) {

        } else if (detectionModelMap == null) {
            detectionModelMap = new HashMap<>();
            detectionModelMap = (HashMap) DetectionControllerImpl.detectResultData.clone();

        } else if (detectionModelMap.isEmpty()) {

            DetectionControllerImpl.detectResultData.entrySet().forEach(entry -> {
                DetectionModel detectionModel = entry.getValue();
                Camera camera = new Camera();
                camera.setId(detectionModel.getCameraId());

                Report lastReport = reportService.getLastReportByCamera(camera);

                // Compare report in database, in case of server restart
                if (lastReport != null) {
                    if (lastReport.getStatus() != detectionModel.getStatusId()) {
                        reportService.saveReport(camera, detectionModel);
                    }
                }
            });

            detectionModelMap = (HashMap) DetectionControllerImpl.detectResultData.clone();
        } else {

            detectionModelMap.entrySet().forEach(entry -> {
                DetectionModel oldResult = entry.getValue();
                int cameraId = oldResult.getCameraId();

                DetectionModel newResult = DetectionControllerImpl.detectResultData.get(cameraId);

                if (oldResult.getStatusId() != newResult.getStatusId()) {
                    Camera camera = cameraService.getCameraById(cameraId);
                    Street street = camera.getStreetByStreetId();

                    // Save new report
                    reportService.saveReport(camera, newResult);

                    String msg = "";
                    switch (newResult.getStatusId()) {
                        case 0:
                            msg = "Khu vực " + camera.getDescription() + ",đường " + street.getName() + ",quận " + street.getDistrict() + " đã thông thoáng";
                            break;
                        case 1:
                            msg = "Kẹt xe ở khu vực " + camera.getDescription() + ",đường " + street.getName() + ",quận " + street.getDistrict();
                            break;
                    }

                    List<String> accountList = bookmarkService.getAccountByCameraId(cameraId);
                    LOGGER.info("list size: " + accountList.size());
                    if (accountList.size() > 0) {
                        for (String account : accountList) {
                            sendNotification(msg, account);
                            LOGGER.info("Notification to user " + account + ": " + msg);
                        }
                    }
                }
            });

            detectionModelMap = (HashMap) DetectionControllerImpl.detectResultData.clone();
        }
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Saigon")
    public void dailyReset() {
        List<Report> reports = reportService.getUnfinishedReport();

        for (Report report : reports) {
            DetectionModel model = new DetectionModel();
            model.setCameraId(report.getCameraByCameraId().getId());
            model.setStatusId(report.getStatus());
            model.setImageUrl(report.getImageUrl());

            String strTime = "23:59:59";
            DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
            try {
                Date d = dateFormat.parse(strTime);
                model.setTime(new Timestamp(d.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }


            reportService.saveReport(report.getCameraByCameraId(),model);
        }
        DetectionControllerImpl.detectResultData.clear();
        detectionModelMap.clear();
    }

    private void sendNotification(String msg, String username) throws JSONException {

        JSONObject body = new JSONObject();
        body.put("to", "/topics/" + username);
        body.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", "Traffic Notification");
        notification.put("body", msg);

        body.put("notification", notification);

        HttpEntity<String> request = new HttpEntity<>(body.toString());

        CompletableFuture<String> pushNotification = androidPushNotificationsService.sendNotification(request);
        CompletableFuture.allOf(pushNotification).join();
    }

}