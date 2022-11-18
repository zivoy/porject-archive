from sys import argv
import enchant
dictenery = enchant.Dict("en_US")
script, filename = argv,"all_possibilities.txt"
target = open(filename, 'w')
target.truncate()
start = 65
end = 122
skipstart = 91
skipend = 96
alllist = []
a = range(start, end+1)
b = range(skipstart, skipend+1)
for i in b:
    a.remove(i)
for num1 in a[0:26]:
    print chr(num1)
    for num2 in a[26:52]:
        for num3 in a[26:52]:
            for num4 in a[26:52]:
                for num5 in a[26:52]:
                    combenation =(chr(num1)+chr(num2)+chr(num3)+chr(num4)+chr(num5))
                    if dictenery.check(combenation):
                        alllist.append(combenation)
print "done that"
for j in alllist:
    target.write(j+"Bear@1")
    if j != alllist[-1]:
        target.write("\n")
target.close()
