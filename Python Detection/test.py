import cv2
import os
import json
import requests
from collections import namedtuple

headers = {'Content-type': 'application/json'}
url='http://localhost:8080/api/camera'
params={'page':1,'size':5}
r=requests.get(url, params=params, headers=headers)
x=json.loads(r.text)
resultCamera = x["data"]["cameraList"]
camera={}
print(x["data"]["cameraList"])
for i in range(0,5):
    camera.update({resultCamera[i]['id']:resultCamera[i]["resource"]})
##print(camera)
print('camera',camera)
for keys,values in camera.items():
     print(keys)
     print(values)

