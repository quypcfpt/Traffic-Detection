package com.spring2019.trafficJamDetectionSystem.service;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public interface AndroidPushNotificationsService {
    @Async
    public CompletableFuture<String> sendNotification(HttpEntity<String> entity);

    public void sendData(int streetId, int cameraId);

}
