int.randint(0,10)
random.ranfint
int_randint()
input = random . randint
import random
def int_randint():
      num = random.randint(0,10)
      
import random
def int_randint():
   num = random.randint(0,10)
   print ("guess a number beteewn 1 and 10")
   while True:
       guess = input ("»»» ")
       if guess == num:
           print("correct")
           break
       elif num < guess:
           print("nope try lower")
       elif num > guess:
           print("nope try higher")
       else:
           print ("nope")
