######## Video Object Detection Using Tensorflow-trained Classifier #########
#
# Author: Evan Juras
# Date: 1/16/18
# Description: 
# This program uses a TensorFlow-trained classifier to perform object detection.
# It loads the classifier uses it to perform object detection on a video.
# It draws boxes and scores around the objects of interest in each frame
# of the video.

## Some of the code is copied from Google's example at
## https://github.com/tensorflow/models/blob/master/research/object_detection/object_detection_tutorial.ipynb

## and some is copied from Dat Tran's example at
## https://github.com/datitran/object_detector_app/blob/master/object_detection_app.py

## but I changed it to make it more understandable to me.

# Import packages
import cv2
import os
import numpy as np
import tensorflow as tf
import sys
import matplotlib.pyplot as plt
from filestack import Client
import datetime
import requests
import json
client = Client("AwCnfiZESWaA14iEjm189z")
# This is needed since the notebook is stored in the object_detection folder.
sys.path.append("..")

# Import utilites
from utils import label_map_util
from utils import visualization_utils as vis_util

# Name of the directory containing the object detection module we're using
MODEL_NAME = 'faster_rcnn_inception_v2_99k_result'


# Grab path to current working directory
CWD_PATH = os.getcwd()

# Path to frozen detection graph .pb file, which contains the model that is used
# for object detection.
PATH_TO_CKPT = MODEL_NAME + '/frozen_inference_graph.pb'

# Path to label map file
PATH_TO_LABELS = os.path.join('training', 'labelmap2.pbtxt')


# Number of classes the object detector can identify
NUM_CLASSES = 5

# Load the label map.
# Label maps map indices to category names, so that when our convolution
# network predicts `5`, we know that this corresponds to `king`.
# Here we use internal utility functions, but anything that returns a
# dictionary mapping integers to appropriate string labels would be fine
label_map = label_map_util.load_labelmap(PATH_TO_LABELS)
categories = label_map_util.convert_label_map_to_categories(label_map, max_num_classes=NUM_CLASSES, use_display_name=True)
category_index = label_map_util.create_category_index(categories)

# Load the Tensorflow model into memory.
detection_graph = tf.Graph()
with detection_graph.as_default():
    od_graph_def = tf.GraphDef()
    with tf.gfile.GFile(PATH_TO_CKPT, 'rb') as fid:
        serialized_graph = fid.read()
        od_graph_def.ParseFromString(serialized_graph)
        tf.import_graph_def(od_graph_def, name='')

    sess = tf.Session(graph=detection_graph)

# Define input and output tensors (i.e. data) for the object detection classifier

# Input tensor is the image
image_tensor = detection_graph.get_tensor_by_name('image_tensor:0')

# Output tensors are the detection boxes, scores, and classes
# Each box represents a part of the image where a particular object was detected
detection_boxes = detection_graph.get_tensor_by_name('detection_boxes:0')

# Each score represents level of confidence for each of the objects.
# The score is shown on the result image, together with the class label.
detection_scores = detection_graph.get_tensor_by_name('detection_scores:0')
detection_classes = detection_graph.get_tensor_by_name('detection_classes:0')

# Number of objects detected
num_detections = detection_graph.get_tensor_by_name('num_detections:0')

# Open video file
video = cv2.VideoCapture('vidTest.MOV')
fps = int(video.get(cv2.CAP_PROP_FPS))
count = 0
success = True
while success:
    success, image = video.read()
    name = './Capture/image' + str(count) + '.jpg'
    def length_of_bounding_box(ymin,ymax):
            return (ymax - ymin)/2 + ymin
    if count%(10*fps) == 0 :
        
        print ('Creating...' + name)
        height, width, channels = image.shape 
    
        image_expanded = np.expand_dims(image, axis=0)

        # Perform the actual detection by running the model with the image as input
        (boxes, scores, classes, num) = sess.run(
            [detection_boxes, detection_scores, detection_classes, num_detections],
            feed_dict={image_tensor: image_expanded})
        threshold = 0.5
        objects = []
        
        for index, value in enumerate(classes[0]):
          object_dict = {}
          box=[]
          if scores[0, index] > threshold:
            #print(len(boxes[0,index]))
            ymin = boxes[0,index][0]* height
            xmin = boxes[0,index][1]* width
            ymax = boxes[0,index][2]* height
            xmax = boxes[0,index][3]* width
            object_dict['type'] =(category_index.get(value)).get('name')
            object_dict['yValue'] =length_of_bounding_box(ymin , ymax)
            object_dict['xValue'] =length_of_bounding_box(xmin , xmax)
            objects.append(object_dict)

        motorCount=0
        carCount=0

        for obj in objects:
            keys = iter(obj.values())
            typeVe,yValue,xValue=next(keys),next(keys),next(keys)
            print(typeVe ,"=",yValue,"=",xValue)
            label="motor"
            if(yValue >=560 and yValue <=650 and xValue>=550 and xValue<=1350):
                if(typeVe.strip() == label):
                    motorCount +=1
                else:
                    carCount+=1
            else:
                 print("No vehicle in area detection")
        print(motorCount)
        print(carCount)
        image[560:700 , 550:1350 , 2]=255
        plt.figure( figsize = (10,10))
        cv2.imwrite(name,image)
        new_filelink = client.upload(filepath=name, multipart=False)
        print('filestack url: ' + new_filelink.url)
        print(datetime.datetime.now().time())
        vehicles ={
	"CameraId":1,
	"result":[
		{
		"typeId":1,
		"countVeh":motorCount
		},
		{
		"typeId":2,
		"countVeh":carCount	
		}
	]
}
        headers = {'Content-type': 'application/json'}
        url='http://localhost:8080/api/detection'
        params={'detectResultString':json.dumps(vehicles)}
        r=requests.post(url, params=params, headers=headers)
        print(r.status_code, r.reason, r.text)
    count+=1
   
    #cv2.imshow('Object detector', image)
    # All the results have been drawn on the frame, so it's time to display it.
    ##cv2.imshow('Object detector', frame)

    # Press 'q' to quit
    if cv2.waitKey(0) == ord('q'):
        break

# Clean up
video.release()
cv2.destroyAllWindows()
