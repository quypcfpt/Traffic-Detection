import cv2
import os
count = 0
CWD_PATH = os.getcwd()

vidcap = cv2.VideoCapture(CWD_PATH + "/video/MVI_3993.MOV")
fps = int(vidcap.get(cv2.CAP_PROP_FPS))

while True:
    success,image = vidcap.read()
    path = './test/' + "_" + str(count) + '.jpg'
    if count%(10*fps)==0:
        print('Creating ' + path)
        cv2.imwrite(path, image)     # save frame as JPEG file      
    
    
    count += 1
