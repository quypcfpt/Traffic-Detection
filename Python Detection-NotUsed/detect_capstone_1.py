import cv2
import os
import numpy as np
import tensorflow as tf
import sys
import matplotlib.pyplot as plt
from filestack import Client
import threading
from xlrd import open_workbook
from utils import label_map_util
from utils import visualization_utils as vis_util

MODEL_NAME = 'faster_rcnn_inception_v2_99k_result'
CWD_PATH = os.getcwd()
PATH_TO_CKPT = MODEL_NAME + '/frozen_inference_graph.pb'
PATH_TO_LABELS = os.path.join('data', 'labelmap2.pbtxt')
NUM_CLASSES = 5

client = Client("AwCnfiZESWaA14iEjm189z")
sys.path.append("..")

label_map = label_map_util.load_labelmap(PATH_TO_LABELS)
categories = label_map_util.convert_label_map_to_categories(label_map, max_num_classes=NUM_CLASSES, use_display_name=True)
category_index = label_map_util.create_category_index(categories)

detection_graph = tf.Graph()
with detection_graph.as_default():
    od_graph_def = tf.GraphDef()
    with tf.gfile.GFile(PATH_TO_CKPT, 'rb') as fid:
        serialized_graph = fid.read()
        od_graph_def.ParseFromString(serialized_graph)
        tf.import_graph_def(od_graph_def, name='')
    sess = tf.Session(graph=detection_graph)
    
image_tensor = detection_graph.get_tensor_by_name('image_tensor:0')
detection_boxes = detection_graph.get_tensor_by_name('detection_boxes:0')
detection_scores = detection_graph.get_tensor_by_name('detection_scores:0')
detection_classes = detection_graph.get_tensor_by_name('detection_classes:0')
num_detections = detection_graph.get_tensor_by_name('num_detections:0')

video_path = [CWD_PATH + "/video/street_720.MP4",CWD_PATH + "/video/IMG_0043.MOV"]
size_excel_path = open_workbook('FixSize.xls')
size_values = []

class ReadFileExcel(object):
        def __init__(self, motor, car, bus, truck, road):
                self.motor = motor
                self.car = car
                self.bus = bus
                self.truck = truck
                self.road = road
#read excel
with open_workbook('FixSize.xls') as f:
        for s in f.sheets():
                for row in range(1, s.nrows):
                        values = []
                        for col in range(s.ncols):
                                value  = (s.cell(row,col).value)
                                try : value = str(float(value))
                                except ValueError : pass
                                finally:
                                        values.append(value)
                        item =  ReadFileExcel(*values)       
                        size_values.append(item)
      
def length_of_bounding_box(ymin,ymax):
        return (ymax - ymin)/2 + ymin

def isDescending(list):
    previous = list[0]
    for number in list:
        if number < previous:
            return False
        previous = number
    return True

class DetectMutilThread (threading.Thread):
    def __init__(self, name, video_url):
        threading.Thread.__init__(self)
        self.name = name
        self.video_url = video_url
   
    def run(self):
        print("Starting " + self.name + " ")
        window_name = self.name
        cv2.namedWindow(window_name)
        video = cv2.VideoCapture(self.video_url)
        fps = int(video.get(cv2.CAP_PROP_FPS))
        count = 0
        density = []
        delta = 0.0
        while True:
            frame, image = video.read()
            path = './capture/' + self.name + "_" + str(count) + '.jpg'
            if count%(10*fps) == 0 :
                print('Creating...' + path)
                height, width, channels = image.shape
                image_expanded = np.expand_dims(image, axis=0)

                (boxes, scores, classes, num) = sess.run(
                    [detection_boxes, detection_scores, detection_classes, num_detections],
                    feed_dict={image_tensor: image_expanded})

                vis_util.visualize_boxes_and_labels_on_image_array(
                    image,
                    np.squeeze(boxes),
                    np.squeeze(classes).astype(np.int32),
                    np.squeeze(scores),
                    category_index,
                    use_normalized_coordinates=True,
                    line_thickness=5,
                    min_score_thresh=0.50)
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
                    typeVehicle,yValue,xValue=next(keys),next(keys),next(keys)
##                    print(typeVehicle ,"=",yValue,"=",xValue)
                    label="motor"
                    if(yValue >=560 and yValue <=650 and xValue>=550 and xValue<=1350):
                        if(typeVehicle.strip() == label):
                            motorCount +=1
                        else:
                            carCount+=1
##                    else:
##                         print("No vehicle in area detection")
                
                cv2.line(image,(0,511),(width,511),(255,0,0),5)
                image[560:700 , 550:1350 , 2]=255
                plt.figure( figsize = (10,10))
                
                for e in size_values:
                        if((motorCount > 0) & (carCount == 0)):
                                x = float(e.motor)*motorCount
                                print("Motor: " + str(motorCount) + " (vehicle) " + self.name + "_" + str(count))
                                density.append(x)
                                print("Density: " + str(x) + " " + self.name + "_" + str(count))
                                print("Density: " + str(density) + " " + self.name + "_" + str(count))
                        elif((carCount > 0) & (motorCount == 0)):
                                y = float(e.car)*carCount
                                print("Car: " + str(carCount) + " (vehicle) " + self.name + "_" + str(count))
                                density.append(y)
                                print("Density: " + str(y) + " " + self.name + " " + str(count))
                                print("Density: " + str(density) + " " + self.name + "_" + str(count))
                        elif((motorCount > 0) & (carCount > 0)):
                                z = (float(e.motor)*motorCount) + (float(e.car)*carCount)
                                print("Motor: " + str(motorCount) + " (vehicle) " + self.name + "_" + str(count))
                                print("Car: " + str(carCount) + " (vehicle) " + self.name + "_" + str(count))
                                density.append(z)
                                print("Density: " + str(z) + " " + self.name + "_" + str(count))
                                print("Density: " + str(density) + " " + self.name + "_" + str(count))
                        else:
                                print("No vehicle in area detection")
                cv2.imwrite(path, image)
                #new_filelink = client.upload(filepath=name, multipart=False)
                #print('filestack url: ' + new_filelink.url)

                #print(datetime.datetime.now().time())
##                vehicles ={
##                        "CameraId":1,
##                        "result":[
##                                {
##                                "typeId":1,
##                                "countVeh":motorCount
##                                },
##                                {
##                                "typeId":2,
##                                "countVeh":carCount	
##                                }
##                        ]
##                }
##                headers = {'Content-type': 'application/json'}
##                url='http://localhost:8080/api/detection'
##                params={'detectResultString':json.dumps(vehicles)}
##                r=requests.post(url, params=params, headers=headers)
##                print(r.status_code, r.reason, r.text)

            if ((count%(60*fps) == 0) & (count != 0)) :
                print("Density: " + str(density) + " " + self.name + " sau 1 phut_" + str(count))
                avgDensity = sum(density)/7
                for e in size_values:
                        delta = avgDensity - float(e.road)
                
                print("Density avag: " + str(avgDensity))
                print("Delta: " + str(delta))
            if not frame:  # error on video source or last frame finished
                break
            
            count+=1
           
            if cv2.waitKey(2) == ord('q'):
                break
     
        video.release()
        cv2.destroyWindow(window_name)
        print(window_name + " Exiting ")
        
def main():
    #thread_lock = threading.Lock()
    for item in video_path: 
        thread = DetectMutilThread(os.path.basename(item), item)
        thread.start()  
        
if __name__ == '__main__':
    main()
