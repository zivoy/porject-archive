import nxt.bluesock as bl

from time import sleep
FREQ_C = 523
FREQ_D = 587
FREQ_E = 659
FREQ_G = 784

from nxt.brick import Brick
from nxt.locator import find_one_brick
from nxt.motor import Motor, PORT_A, PORT_B, PORT_C
from nxt.sensor import Light, Sound, Touch, Ultrasonic
from nxt.sensor import PORT_1, PORT_2, PORT_3, PORT_4

class lego:
    def __init__(self):
        l =     bl.find_bricks()
        br =     l.next()
        brick = bl.BlueSock(br.host).connect() 
        self.br = br
        self.brick = brick
        self.light = Light(self.brick,PORT_3)
        self.A = Motor(self.brick,PORT_A)
    def forward(self):
        self.A.run()
        sleep(1)
        self.A.brake()
    def see(self):
        print self.light.get_sample()
    def play_song(self):
        b = self.brick
        b.play_tone_and_wait(FREQ_E, 500)

        b.play_tone_and_wait(FREQ_D, 500)
        b.play_tone_and_wait(FREQ_C, 500)
        b.play_tone_and_wait(FREQ_D, 500)
        b.play_tone_and_wait(FREQ_E, 400)
        sleep(0.1)
        b.play_tone_and_wait(FREQ_E, 400)
        sleep(0.1)
        b.play_tone_and_wait(FREQ_E, 500)
        sleep(0.5)
        b.play_tone_and_wait(FREQ_D, 400)
        sleep(0.1)
        
        b.play_tone_and_wait(FREQ_D, 400)
        sleep(0.1)
        b.play_tone_and_wait(FREQ_D, 500)
        sleep(0.5)
        b.play_tone_and_wait(FREQ_E, 500)
        b.play_tone_and_wait(FREQ_G, 400)
        sleep(0.1)

        b.play_tone_and_wait(FREQ_G, 500)
        sleep(0.5)
        b.play_tone_and_wait(FREQ_E, 500)
        b.play_tone_and_wait(FREQ_D, 500)
        b.play_tone_and_wait(FREQ_C, 500)
        b.play_tone_and_wait(FREQ_D, 500)
        b.play_tone_and_wait(FREQ_E, 400)
        sleep(0.1)
        b.play_tone_and_wait(FREQ_E, 400)
        sleep(0.1)
        b.play_tone_and_wait(FREQ_E, 400)
        sleep(0.1)
        
        b.play_tone_and_wait(FREQ_E, 500)
        b.play_tone_and_wait(FREQ_D, 500)
        b.play_tone_and_wait(FREQ_D, 500)
        b.play_tone_and_wait(FREQ_E, 500)
        b.play_tone_and_wait(FREQ_D, 500)
        b.play_tone_and_wait(FREQ_C, 750)
        sleep(1)
