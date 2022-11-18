nums = [int(i) for i in input().split()]

def getFer(c):
    return c * 1.8 + 32

found = False
for i in range(2):
    for j in range(2):
        for k in range(2):
            chk = nums.copy()
            if i:
                chk[0] = getFer(nums[0])
            if j:
                chk[1] = getFer(nums[1])
            if k:
                chk[2] = getFer(nums[2])
            print(nums, i ,j, k)
            if chk == sorted(chk):
                found = True
                break
        if found:
            break
    if found:
        break

if found:
    print("Possible")
else:
    print("Impossible")

