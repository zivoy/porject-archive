import cv2
import pytesseract
from PIL import ImageGrab
pytesseract.pytesseract.tesseract_cmd = r"C:\Program Files\Tesseract-OCR\tesseract.exe"
import numpy as np

#img = cv2.imread(r"C:\Users\zivno\Downloads\breakingnews.png")
#text = pytesseract.image_to_string(img)
#print(text)

def grab(place):
    img = ImageGrab.grab(bbox=place) #bbox specifies specific region (bbox= x,y,width,height *starts top-left)
    return cv2.cvtColor(np.array(img), cv2.COLOR_RGB2BGR)

def grabAndShow(place):
    frame= grab(place)
    cv2.imshow("test", frame)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

def grabAndRead(place):
    img = grab(place)
    return pytesseract.image_to_string(img)

grabAndShow((700,800,1220,880))
