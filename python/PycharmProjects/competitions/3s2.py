input()
string = input().lower()
vow = "aeiou"
cCount = 0
vCount = 0

spaces = 0

for i in string:
    if i in vow:  # is vowel
        if vCount != 0:
            vCount = 0
        cCount += 1
    else:
        if cCount != 0:
            cCount = 0
        vCount += 1
    if vCount == 4:
        vCount -= 3
        spaces +=1
    if cCount == 4:
        cCount -= 3
        spaces += 1

print(spaces)
