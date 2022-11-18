def az():
    for num in range(97,123):
        print chr(num),
        
#
# challenge for tomorrow
# write a function called run_abc() that asks the user for a letter
# and prints the abc up to that letter.
# for example:

# >>> run_abc()
# enter a letter>
# h
# a b c d e f g h

def run_abc():
    print "chose a letter and write it"
    letter = raw_input()
    num=ord(letter)
    for an in range(97,num+1):
        ans=chr (an)
        print ans,
        
        
def run_xg():
    print "forst chose the forst letter and write it"
    fletter = raw_input()
    print "now chose the secend letter and write it"
    lletter = raw_input()
    fnum=ord(fletter)
    lnum=ord(lletter)
    for an in range(fnum,lnum+1):
        ans=chr (an)
        print ans,
from random import randint
def run_guess():
    a = randint(5,15)
    b = randint(1,10)
    print "guess what is ",a,"x",b
    number = a*b
    for loop in range(100):
        guess = int(raw_input ())
        if guess < number :
            print guess ,"is to low" 
            print "guess again"
        elif guess > number :
            print guess ,"is to high" 
            print "guess again"
        else:
            print guess ,"is correct"
            break


def run_guess2():
    print "guess what is 61*13"
    number = 61*13
    guess = int(raw_input ())
    if guess < number :
        print guess ,"is to low"
        run_guess2()
    if guess > number :
        print guess ,"is to high"
        run_guess2()
    if guess == number:
        print guess ,"is correct"

def do_something_10_times():
    for num in range(10):
        print "ziv"

def hanger():
    word = 'ziv'
    guesses = list()
    for times in range(10):
        print "geuss a letter "#the word is _ _ _"
        answer = raw_input()
        guesses.append(answer)
        print   "your guesses are ",guesses
        
        if  answer in word:
            
            print answer,"is in the word"
        else:
            print answer,"is not in the word"

#        missing = False
        for letter in word:
            if  letter in guesses :
                print letter,
            else:
                print '_',
     #           missing = True
        print [letter  not in guesses for letter in word]
        missing = any(letter  not in guesses for letter in word)          
        if not missing:
            print "is correct"
            break
    
