"""def count(string, letter):
    pos = 0
    if string[0]==letter:
        while string[pos] == letter:
            pos-=1
        pos+=1
    else:
        while string[pos] != letter:
            pos+=1

    amount= 0
    while string[pos%len(string)] == letter:
        pos += 1
        amount +=1

    return amount


string = "AABBBCCCCA" #"BABCBCACCA"
for i in "ABC":
    if count(string, i) != string.count(i):
        print("not good")
    else:
        print("fine")
"""
string = "BABCBCACCA"
import re
while True:
    a = list(re.finditer("A+", string))
    b = list(re.finditer("B+", string))
    c = list(re.finditer("C+", string))
    print(a,b,c)