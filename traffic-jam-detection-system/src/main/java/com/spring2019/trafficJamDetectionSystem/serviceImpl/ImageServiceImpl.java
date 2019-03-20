package com.spring2019.trafficJamDetectionSystem.serviceImpl;

import com.spring2019.trafficJamDetectionSystem.entity.Image;
import com.spring2019.trafficJamDetectionSystem.repository.ImageRepository;
import com.spring2019.trafficJamDetectionSystem.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    ImageRepository imageRepository;
    @Override
    public Image getImageByCameraID(int id) {
        Optional<Image> image = imageRepository.getByCameraId(id);
        return image.orElse(null);
    }

    @Override
    public void updateImage(Image image) {
        imageRepository.save(image);
    }
}
