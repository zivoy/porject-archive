from PIL import Image, ImageEnhance
import numpy as np
import os

if not os.path.exists("parts"):
    os.mkdir("parts")


def MakeMasks(image, contrast=100):
    # load image
    im = Image.open(image)

    # turn BW and remove aa
    im = im.convert('LA')
    im = ImageEnhance.Contrast(im).enhance(contrast)

    temp = np.array(im)
    temp = (np.round(temp/255)*255).astype(np.uint8)
    im = Image.fromarray(temp)

    im = im.convert('RGBA')

    # island detection
    data = np.array(im)
    islands = list()
    x = 0
    y = 0
    while True:
        found = False
        while x < data.shape[1]:
            while y < data.shape[0]:
                # print(i)
                if (data[y][x][:3] == (0, 0, 0)).all():  # is black
                    found = True
                    # print(i, j, data[i][j])
                    break
                y += 1
            if found:
                break
            x += 1
            y = 0
        else:
            # no blacks found exit
            break

        # find connected blacks
        island = np.tile(False, data.shape[:2])
        checked = set()
        tocheck = [(y, x)]
        while tocheck:
            i = tocheck.pop()
            if (data[i[0]][i[1]][:3] == (0, 0, 0)).all():
                island[i[0]][i[1]] = True
                for j in range(-1, 2):
                    for k in range(-1, 2):
                        # not this
                        if j == k == 0:
                            continue

                        # not out of border
                        t1 = i[0] + j
                        t2 = i[1] + k
                        if (t1 < 0 or t2 < 0) and (t1 >= data.shape[0] or t2 >= data.shape[1]):
                            continue

                        # not checked
                        t3 = (t1, t2)
                        if t3 in checked:
                            continue
                        # to check
                        tocheck.append(t3)

            checked.add(i)

        # remove island
        data[..., :-1][island] = (255, 255, 255)
        islands.append(island)

    # masking
    arr = np.array([0, 0, 0, 255], dtype=np.uint8)
    emptyMask = np.tile(arr, (*data.shape[:2], 1))

    for i in islands:
        maskData = emptyMask.copy()
        maskData[..., :-1][i] = (255, 255, 255)

        yield Image.fromarray(maskData)
