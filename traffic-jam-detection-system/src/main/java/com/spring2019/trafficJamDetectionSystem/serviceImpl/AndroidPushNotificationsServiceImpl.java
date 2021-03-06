package com.spring2019.trafficJamDetectionSystem.serviceImpl;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import com.spring2019.trafficJamDetectionSystem.common.HeaderRequestInterceptor;
import com.spring2019.trafficJamDetectionSystem.service.AndroidPushNotificationsService;
import javafx.util.StringConverter;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Service
public class AndroidPushNotificationsServiceImpl implements AndroidPushNotificationsService {

    private static final String FIREBASE_SERVER_KEY = "AAAAp59Wlis:APA91bGms8UBe6lo38LryTLhi9KtGepmSaYsKL5INZl9JLADM6VWvj-19MYNJ4CRm-gLegm2dFkAoGD9CGXxbKNnB0U_lZixK6I4QQDMM759Z-1AXePbhxzYYSyCIiluRDSDI3wUkmA6";
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

    @Override
    public CompletableFuture<String> sendNotification(HttpEntity<String> entity) {
        RestTemplate restTemplate = new RestTemplate();

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }

    @Override
    public void sendData(int cameraId, int status, String time, String img) {
        JSONObject body = new JSONObject();
        body.put("to", "/topics/" + CoreConstant.FIREBASE_GENERAL_TOPIC);
        body.put("priority", "high");

        JSONObject data = new JSONObject();
        data.put("camera", cameraId);
        data.put("status", status);
        data.put("time", time);
        data.put("img", img);
        body.put("data", data);

        HttpEntity<String> request = new HttpEntity<>(body.toString());

        CompletableFuture<String> pushNotification = sendNotification(request);
        CompletableFuture.allOf(pushNotification).join();
    }
}
