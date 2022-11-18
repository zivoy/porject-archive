from random import random
from hashlib import sha224
from time import sleep
from collections import Counter

def bid(points):
    while True:
        noNum = "that wasn't a number!?!?"
        try:
            bid = int(input("you have %s points how many do you want to bid: " %points))
            while bid<10 or bid>points:
                if bid < 10:
                    while bid < 10:
                        bid = int(input("your bid was too low it has to be at least 10 if you still have %s points: " %points))
                elif bid > points:
                    while bid > points:
                        bid = int(input("the you don't have enough points for your bid you have %s points: " %points))
            break
        except ValueError:
            print (noNum)
        except SyntaxError:
            print (noNum)
        except NameError:
            print (noNum)
    return bid

def all_bids(point,bids):
    key = raw_input("pick your color: ").lower()
    while True:
      if key == "red" or key == "green" or key == "black":
        break
      else:
        key = raw_input("you didn't pick a valid color: ")
    bids[key]+=bid(point)
    return bids

def play_slot():
    score = 20
    bids = Counter({"green": 0, "red": 0 ,"black": 0})
    print ("red and black x2 your points and green x14 them\n*hint red and black have a 7/15 (46.67%) chance of happening and green has a 1/15 (6.67) chance of happening")
    while(True):        
        wining = random()
        hashed = sha224(str(wining)).hexdigest()
        wining_number = str(int(round(wining*15)))
        if int(wining_number) == 0:
            wining_color = "green"
        elif int(wining_number) <= 7:
            wining_color = "black"
        else:
            wining_color = "red"
        wining = str(wining)
        print ("this rounds hash is "+hashed)
        
        bids = all_bids(score,bids)
        score -= sum(bids.values())
        print ("score "+str(score))
        while score>=10:
            if raw_input("do you want to bid again yes or no: ").lower()=="yes":
              temp = Counter() 
              temp += bids
              all_bids(score,bids)
              youLost = bids-temp
              score -= sum(youLost.values())
            #  print "score now "+str(score)
              print
            else:
                break
        sleep(1)
        print ("the wining color is "+wining_color),
        print ("and the winner is "+wining_number)
        print ("the number was "+wining)
        if wining_color == "red" or wining_color == "black":
            youwon = bids[wining_color]*2
        elif wining_color == "green":
            youwon = bids[wining_color]*14
        score+=youwon
        print ("you won "+str(youwon)+" points. you now have "+str(score)+" points")
        bids = Counter({"green": 0, "red": 0 ,"black": 0})
        if score<10:
            s = "s"
            if score == 1:
              s =  ""
            print ("you have "+str(score)+" point"+s+" left so you don't have enough points to continue playing")
            break
        if raw_input("press enter to try again or type \'exit\' to exit: ").lower()=="exit":
          break
        print

play_slot()
