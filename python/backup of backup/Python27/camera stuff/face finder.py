import cv2
from time import sleep

cam = cv2.VideoCapture(0)

winName = "face finder"
cv2.namedWindow(winName)

cascade = cv2.CascadeClassifier('haarcascade_frontalface_default.xml')

def facedetect(img):
    gray = cv2.cvtColor(img, cv2.COLOR_RGB2GRAY)
    gray = cv2.equalizeHist(gray)
    faces = cascade.detectMultiScale(gray, scaleFactor=1.2, minNeighbors=2,
                                     minSize=(80, 80),
                                     flags = cv2.CASCADE_SCALE_IMAGE)
    if len(faces) == 0:
        return []
    else:
        for f in faces:
            print f
        return faces
camSee = cam.read()[1]
cv2.imshow(winName, camSee)
sleep(1)
while True:
    camSee = cam.read()[1]
    cords = facedetect(camSee)
    img = cv2.rectangle(camSee,(cords[0],cords[1]),(cords[2],cords[3]),(0,255,0),3)
    cv2.imshow(winName, img)
    key = cv2.waitKey(10)
    if key == 27:
        cam.release()
        cv2.destroyWindow(winName)
        break
