import random
num = random.randint(0,20)

print("i have picked a number between 1 and 20 make your guess")
while True:
    guess = int(raw_input(">>> "))
    if guess == num:
        print("that's correct the number was "+str(num))
        break
    else:
        print("nope try again")
