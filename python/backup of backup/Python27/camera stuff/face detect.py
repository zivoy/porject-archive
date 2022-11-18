import cv2
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
        print faces
        return faces

image = cv2.imread("DSC_0852.JPG")
facedetect(image)
