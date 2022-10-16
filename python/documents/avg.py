nums=0
curr=0
while True:
    i = input()
    if i == "exit":
        break
    i= float(i)
    nums+=1
    curr = (curr*(nums-1)+i)/nums
    print(">>",curr)

print()
print(nums,"--",curr)