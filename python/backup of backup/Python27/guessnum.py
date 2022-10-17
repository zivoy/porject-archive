# the computer picks a number V

# -----v
# it asks you if you want to guess the number or more information. v
# if you guess 
#       it tells you right or wrong
# if you ask for more information,
#       the player has to pick a test number.
#
#and the computer tells you what is the remainder when you divide the computer number by the test number


import random
from time import sleep
def menu_options():
    return raw_input("do you want to guess it or more information about it \n1) guess \n2) more information\n > ")
def getnumber():
    return random.randint(20,30)
def start():
    computer_number = getnumber()
    print ('i thought of a number')
    sleep(2)
    while  True:
        option = menu_options()
        if option == '2':
            test_number = int(raw_input ('put in a test number and i will devide it an give you the remainder \n > '))
            if test_number > 9:
                print "number must be <10"
                continue        
            remainder = computer_number % test_number
            print "the remainder is,", remainder
        
        if option == '1':
            player_number = int(raw_input ("ok put in your guass \n >"))
            if computer_number == player_number:
                print ('you win :)')
                break

            else:
                print "you lose :( too bad the anser was" , computer_number
                break

