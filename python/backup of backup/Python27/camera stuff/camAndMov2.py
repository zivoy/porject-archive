import cv2
from time import sleep

def diffImg(t0, t1, t2):
  i0 = cv2.log(t0 * 1.1)
  i1 = cv2.log(t1 * 1.1)
  i2 = cv2.log(t2 * 1.1)
  d1 = cv2.absdiff(i2, i1)
  o1 = cv2.compare(d1, 2, cv2.CMP_GT)
  d2 = cv2.absdiff(i1, i0)
  o2 = cv2.compare(d2, 2, cv2.CMP_GT)
  return cv2.bitwise_and(o1, o2)

def rbgDevide(img):
  b,g,r = cv2.split(img)

cam = cv2.VideoCapture(0)

sleep(1)

winName = "Movement Indicator"
winName2 = "Normal Camera"
winName3 = "RBG Devide Movement Indicater"
cv2.namedWindow(winName)
cv2.namedWindow(winName2)
#cv2.namedWindow(winName3)

t_minus = cv2.cvtColor(cam.read()[1], cv2.COLOR_RGB2GRAY)
t = cv2.cvtColor(cam.read()[1], cv2.COLOR_RGB2GRAY)
t_plus = cv2.cvtColor(cam.read()[1], cv2.COLOR_RGB2GRAY)

while True:
  cv2.imshow( winName, diffImg(t_minus, t, t_plus) )
  cv2.imshow(winName2, cam.read()[1])
  
  t_minus = t
  t = t_plus
  t_plus = cv2.cvtColor(cam.read()[1], cv2.COLOR_RGB2GRAY)

  key = cv2.waitKey(10)
  if key == 27:
    cam.release()
    cv2.destroyAllWindows()
    break
