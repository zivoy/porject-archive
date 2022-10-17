import random

strin = '226 '
for i in range(3):
    strin+=str(random.randint(0,9))
strin+=' '
for i in range(4):
    strin+=str(random.randint(0,9))
print (strin)