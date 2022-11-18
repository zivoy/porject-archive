import random
from time import time
def quiz():
    game_on = True
    score = 0
    avg_time = []
    while game_on:
        a = random.randint(2,10)
        b = random.randint(2,10)
        if a<5 and b<5:
            continue
        try:
            while True:
                if score == 100:
                    avg_took = sum(avg_time)/len(avg_time)
                    print("you win your average time is", round(avg_took,2),"seconds to anser a qustion")
                    game_on = False
                    break
                start = time()
                ans = input("what is %d x %d: "%(a,b))
                took = round(time() - start,2)
                if int(ans) == a * b:
                    score += 1
                    print ("Right  score is %d"%(score))
                    avg_time.append(took)
                    break
                else:
                    print ("ooops you took:",took,"seconds to anser that")
        except:
            pass
quiz()
