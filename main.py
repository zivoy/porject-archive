from PIL import Image
import numpy as np
import os

if not os.path.exists("parts"):
    os.mkdir("parts")


def equals(value, target, lamda=0):
    return abs(value - target) <= lamda


def unique(array):
    s = array.shape
    tmp = array.reshape((s[0] * s[1], s[2]))
    return np.unique(tmp, axis=0)


file = "sample.png"
im = Image.open(file)
im = im.convert('RGBA')

data = np.array(im)
red, green, blue, alpha = data.T

arr = np.array([0, 0, 0, 255], dtype=np.uint8)
emptyMask = np.tile(arr, (data.shape[0], data.shape[1], 1))

for j, i in enumerate(unique(data)):
    maskData = emptyMask.copy()
    lamda = 0
    print(i)
    highlighted = equals(red, i[0], lamda) & equals(blue, i[2], lamda) & equals(green, i[1], lamda)
    maskData[..., :-1][highlighted.T] = (255, 255, 255)

    mask = Image.fromarray(maskData)
    mask.save(os.path.join("parts", f"{'.'.join(file.split('.')[:-1])}-mask-{j}.png"))
