package com.spring2019.trafficJamDetectionSystem.service;


import com.spring2019.trafficJamDetectionSystem.entity.Image;
import org.springframework.stereotype.Service;

@Service
public interface ImageService  {
    public Image getImageByCameraID(int id);
    public void updateImage(Image image);
}
