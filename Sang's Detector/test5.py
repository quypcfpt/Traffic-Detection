from imageai.Detection import VideoObjectDetection
import os
import cv2

execution_path = os.getcwd()

##fps = int(video.get(cv2.CAP_PROP_FPS))


detector = VideoObjectDetection()
detector.setModelTypeAsRetinaNet()
detector.setModelPath( os.path.join(execution_path , "resnet50_coco_best_v2.0.1.h5"))
detector.loadModel()

custom_objects = detector.CustomObjects(car=True, motorcycle=True, truck=True, bus=True)

video_path = detector.detectCustomObjectsFromVideo(custom_objects=custom_objects, input_file_path=os.path.join(execution_path, "MVI_3990_30s_yellow.avi"),
                                output_file_path=os.path.join(execution_path, "traffic_custom_detected6"),
                                frames_per_second=20, log_progress=True, minimum_percentage_probability=30)
print(video_path)

