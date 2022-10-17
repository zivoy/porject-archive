import time
import io
from PIL import Image, ImageFont, ImageDraw
from typing import List
from imgcomp import FuzzyImageCompare
import numpy as np
import cv2
import threading
import signal
import psutil
from collections import deque
import sys
import picamera

framerate = 2  # fps
backFill = .5  # minutes
endFill = .2  # minutes
resetFrames = 1  # minutes in idle

tolerance = 40  # percent
resetTolerance = 2  # percent

backFill = round(backFill * 60 * framerate)
endFill = round(endFill * 60 * framerate)
resetFrames = round(resetFrames * 60 * framerate)


class CapturedImage:
    def __init__(self, pil_image, image_name, time_taken, compared_to):
        self.drawn_image = pil_image.copy()
        self.image: Image.Image = pil_image
        self.image_name = image_name
        self.time_taken = time_taken
        if compared_to is not None:
            cmp = FuzzyImageCompare(pil_image, compared_to.image)
            self.difference = cmp.difference()
        else:
            self.difference = 0


baseImage: CapturedImage
pictureHistory = deque()

print("Initing camera")
camera = picamera.PiCamera()
camera.resolution = (1280, 720)
camera.framerate = framerate
# Wait for the automatic gain control to settle
time.sleep(2)
# Now fix the values
camera.shutter_speed = camera.exposure_speed
camera.exposure_mode = 'off'
g = camera.awb_gains
camera.awb_mode = 'off'
camera.awb_gains = g
# camera.use_video_port = True
print("Camera setup complete!")

font = ImageFont.truetype("/opt/Wolfram/WolframEngine/12.0/SystemFiles/Fonts/TrueType/RobotoSlab-Light.ttf", 32)


def take_picture(compare_target):
    stream = io.BytesIO()

    currTime = time.asctime()
    uTime = time.time()

    print(f"{currTime} -- Capturing image", end=" ...")

    camera.capture(stream, format='jpeg', use_video_port=True)

    stream.seek(0)
    image = Image.open(stream)

    img_obj = CapturedImage(image, f"{currTime} - shot.jpg", uTime, compare_target)

    draw = ImageDraw.Draw(img_obj.drawn_image)
    draw.text((5, 5), f"{currTime} - {img_obj.difference:.2f}%", (255, 255, 255, 127), font=font)

    stream.close()
    return img_obj


running = True

savers = list()


def main():
    global pictureHistory, baseImage, savers

    movement = False
    counter = endFill

    avgs = list()

    for i in savers:
        if not i.isAlive():
            i.join()

    prev = baseImage

    while running:
        # time.sleep(1)

        if (time.time() - prev.time_taken) > (1 / framerate):
            image = take_picture(baseImage)
            prev = image
            pictureHistory.appendleft(image)
            avgs.append(image.difference)

            print(f"{image.difference:.2f}% different from base", end="")
            if image.difference > tolerance:
                # a difference is detected
                if not movement:
                    print("\nstarting capture")
                movement = True
                counter = endFill
            elif movement:
                # no movement adding padding to end
                counter -= 1

            # if len(pictureHistory) >= resetFrames:
            # reset base image
            last = pictureHistory[-1]
            cmp = FuzzyImageCompare(last.image, baseImage.image)
            avgB = avg(avgs[:resetFrames])
            print(f" - abs diff to avrage : {abs(avgB - cmp.difference()):.2f}%")
            if abs(avgB - cmp.difference()) <= resetTolerance / 2 and len(pictureHistory) > 3 and avgB > resetTolerance:
                print("resetting reference image image")
                baseImage = last
            # else:
            #     print()

            if counter <= 0 or (not running and movement) or psutil.virtual_memory().percent > 70:
                print(f"\nsaving sequence... {psutil.virtual_memory().percent}% ram usage")
                movement = False
                counter = endFill
                x = threading.Thread(target=save_sequence, args=([pictureHistory.pop().drawn_image for _ in
                                                                  range(len(pictureHistory))],))
                x.start()
                savers.append(x)

            if not movement:
                # cut frames for rolling frame
                while len(pictureHistory) > backFill:
                    pictureHistory.pop()
                avgs = avgs[:resetFrames+3]


def avg(itemList):
    return sum(itemList) / len(itemList)


def save_sequence(sequence):
    # videodims = (1280, 720)
    fourcc = cv2.VideoWriter_fourcc(*'avc1')
    video = cv2.VideoWriter(f"/tmp/{time.asctime()} - porch capture.mp4", fourcc, framerate, camera.resolution)
    for i in sequence:
        # draw frame specific stuff here.
        video.write(cv2.cvtColor(np.array(i), cv2.COLOR_RGB2BGR))
    video.release()
    print("...sequence saved")


exited = False


def exit_handler(signal, frame):
    global running, exited
    running = False
    print("exiting")
    if exited:
        camera.close()
        sys.exit(0)
    else:
        exited = True
        print("press ctrl c again to exit")


if __name__ == "__main__":
    signal.signal(signal.SIGINT, exit_handler)
    print("Capturing base image")
    baseImage = take_picture(None)
    print()
    # baseImage.show()
    pictureHistory.appendleft(baseImage)
    main()
    # baseImage.save("image.jpg")
    # time.sleep(5)
    camera.close()
    if savers:
        print("waiting for all sequences to save")
    m = len(savers)
    c = 0
    for i in savers:
        if not i.isAlive():
            c += 1
            print(f"thread {c}/{m} joined")
            i.join()
