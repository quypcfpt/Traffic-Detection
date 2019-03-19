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
import pandas as pd

client = Client("AwCnfiZESWaA14iEjm189z")
sys.path.append("..")



video_path = [CWD_PATH + "/video/MVI_3984_3p.mp4"]
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
        delta = []
        
        while True:
            frame, image = video.read()
            path = './capture/' + self.name + "_" + str(count) + '.jpg'
            if count%(10*fps) == 0 :
                print('Creating...' + path)
                
                    
                motorCount=0
                carCount=0
                truckCount=0
                busCount=0
                
                for obj in objects:
                    keys = iter(obj.values())
                    typeVehicle,yValue,xValue=next(keys),next(keys),next(keys)
                    motorLabel="motor"
                    carLabel="car"
                    truckLabel="truck"
                    busLabel="bus"
                    
                    if(yValue >=250 and yValue <=450 and xValue >= 200 and xValue <= 1100):
                        if(typeVehicle.strip() == motorLabel):
                                motorCount +=1
                        elif(typeVehicle.strip() == carLabel):
                                carCount+=1
                        elif(typeVehicle.strip() == truckLabel):
                                truckCount+=1
                        else:
                                busCount+=1

                font = cv2.FONT_HERSHEY_SIMPLEX
                cv2.putText(
                    image,
                    'Car: ' + str(carCount),
                    (50,70),
                    font,
                    2,
                    (0,0,255),
                    3,
                    )
                cv2.putText(
                    image,
                    'Motor: ' + str(motorCount),
                    (50,150),
                    font,
                    2,
                    (0,0,255),
                    3,
                    )   
                cv2.putText(
                    image,
                    'Bus: ' + str(busCount),
                    (50,230),
                    font,
                    2,
                    (0,0,255),
                    3,
                    )

                cv2.putText(
                    image,
                    'Truck: ' + str(truckCount),
                    (50,310),
                    font,
                    2,
                    (0,0,255),
                    3,
                    )
                image[250:450, 200:1100, 2]=255
                
                
                for e in size_values:
                        if(motorCount <= 0) and (carCount <=0) and (truckCount <= 0) and (busCount <= 0) or (float(e.road) <= ((float(e.motor)*motorCount) + (float(e.car)*carCount) + (float(e.car)*truckCount) + (float(e.car)*busCount))):
                                print("No vehicle in area detection " + " of " + self.name + " in frame_" + str(count))
                                delta.append(0)
                        else:
                                x = float(e.road) - ((float(e.motor)*motorCount) + (float(e.car)*carCount) + (float(e.car)*truckCount) + (float(e.car)*busCount))
                                delta.append(x)
                                print("Object is appended into delta: " + str(x) + " of " + self.name + " in frame_" + str(count))
                                print("Current Delta: " + str(delta) + " of " + self.name + " in frame_" + str(count))

                cv2.imwrite(path, image)

            if ((count%(60*fps) == 0) & (count != 0)) :
                print("Delta after 30s: " + str(delta) + " of " + self.name + " in frame_" + str(count))

                
            if not frame:  # error on video source or last frame finished
                break
            
            count+=1
           
            if cv2.waitKey(2) == ord('q'):
                break
     
        video.release()
        cv2.destroyWindow(window_name)
        print(window_name + " Exiting ")
        
def main():       
    for item in video_path:
        thread = DetectMutilThread(os.path.basename(item), item)
        thread.start()  
        
if __name__ == '__main__':
    main()
