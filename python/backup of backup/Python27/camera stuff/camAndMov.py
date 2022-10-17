import cv2
from time import sleep

def diffImg(t0, t1, t2):
  d1 = cv2.absdiff(t2, t1)
  d2 = cv2.absdiff(t1, t0)
  return cv2.bitwise_and(d1, d2)

def rbgDevide(img):
  b,g,r = cv2.split(img)

cam = cv2.VideoCapture(0)

sleep(1)

winName = "Movement Indicator"
winName2 = "Normal Camera"
cv2.namedWindow(winName)
cv2.namedWindow(winName2)

t_minus = cv2.cvtColor(cam.read()[1], cv2.COLOR_RGB2GRAY)
t = cv2.cvtColor(cam.read()[1], cv2.COLOR_RGB2GRAY)
t_plus = cv2.cvtColor(cam.read()[1], cv2.COLOR_RGB2GRAY)

while True:
  cv2.imshow( winName, diffImg(t_minus, t, t_plus))
  img = cam.read()[1]
  font = cv2.FONT_HERSHEY_SIMPLEX
  img = cv2.putText(img,'press esc to close',
              (10,460), font, 2,(255,255,255),1,cv2.LINE_AA)
  cv2.imshow(winName2, img)
  
  t_minus = t
  t = t_plus
  t_plus = cv2.cvtColor(cam.read()[1], cv2.COLOR_RGB2GRAY)

  key = cv2.waitKey(10)
  if key == 27:
    cam.release()
    cv2.destroyAllWindows()
    break
