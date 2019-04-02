package com.spring2019.trafficJamDetectionSystem.schedule;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import com.spring2019.trafficJamDetectionSystem.controllerImpl.DetectionControllerImpl;
import com.spring2019.trafficJamDetectionSystem.entity.Account;
import com.spring2019.trafficJamDetectionSystem.entity.Street;
import com.spring2019.trafficJamDetectionSystem.model.DetectionModel;
import com.spring2019.trafficJamDetectionSystem.service.AndroidPushNotificationsService;
import com.spring2019.trafficJamDetectionSystem.service.BookmarkService;
import com.spring2019.trafficJamDetectionSystem.service.CameraService;
import com.spring2019.trafficJamDetectionSystem.service.StreetService;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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
    AndroidPushNotificationsService androidPushNotificationsService;

    @Scheduled(fixedDelay = 10 * 1000)
    public void scheduleFixedDelayTask() {

        if (DetectionControllerImpl.detectResultData == null) {

        } else if (detectionModelMap == null) {
            detectionModelMap = new HashMap<>();

            detectionModelMap = (HashMap) DetectionControllerImpl.detectResultData.clone();

        } else {
            detectionModelMap.entrySet().forEach(entry -> {
                DetectionModel oldResult = entry.getValue();
                int cameraId = oldResult.getCameraId();

                DetectionModel newResult = DetectionControllerImpl.detectResultData.get(cameraId);

                if (oldResult.getStatusId() != newResult.getStatusId()) {
                    Street street = cameraService.getCameraById(cameraId).getStreetByStreetId();


                    String msg = "";
                    switch (newResult.getStatusId()) {
                        case 0:
                            msg = "Đường " + street.getName() + " quận " + street.getDistrict() + " đã thông thoáng";
                            break;
                        case 1:
                            msg = "Kẹt xe ở đường " + street.getName() + " quận " + street.getDistrict();
                            break;
                    }

                    List<String> accountList = bookmarkService.getAccountByCameraId(cameraId);
                    LOGGER.info("list size: " + accountList.size());
                    if (accountList.size() > 0) {
                        for (String account : accountList) {
                            sendNotification(msg, account);
                            LOGGER.info("Notification to user "+account +": " + msg);
                        }
                    }
                }
            });

            detectionModelMap = (HashMap) DetectionControllerImpl.detectResultData.clone();
        }
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

//        try {
//            String firebaseResponse = pushNotification.get();
//            LOGGER.info(msg);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
    }
}