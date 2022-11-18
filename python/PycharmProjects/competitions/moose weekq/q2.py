a = []
for i in range(int(input())):
    d = int(input())

    if len(a) == 0:
        a.append(d)
        continue

    lst = range(len(a))
    if abs(d-a[-1]) < abs(d-a[0]):
        lst = lst[::-1]

    for j in lst:
        if a[j] >= d:
            a = a[:j] + [d] + a[j:]
            break
    else:
        a.append(d)

[print(i) for i in a]
