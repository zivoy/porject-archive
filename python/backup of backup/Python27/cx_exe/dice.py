from random import randint
from pyfiglet import figlet_format

def mulipledice(numOfDice):
    dice = []
    while numOfDice!=0:
        dice.append(randint(1,6))
        numOfDice-=1
    return sum(dice)

def dice():
    while True:
        try:
            numOfDice = int(raw_input("input the numeber of dice: "))
            break
        except ValueError:
            print ("that is not a number")
    while True:
        if raw_input(figlet_format(str(mulipledice(numOfDice)),font="big"))!= "":
            break

dice()
