input()

pres = sorted([int(i) for i in input().split()])



def diffL(inl):
    f = list()
    for i in range(1, len(inl)):
        f.append(inl[i] - inl[i-1])
    return f


def nextBiggetDiff(data):
    diff = diffL(data)

    d = dict()
    for i, j in enumerate(diff):
        d[i] = j
    diff = sorted(d.items(), key=lambda kv: (kv[1], kv[0]))[::-1]
    try:
        return data[diff[0][0]+1]
    except:
        return data[0]


a = 0
o = 0
ash = True
prev = 5e5+1

for i in range(len(pres)):
    if not ash:
        value = max(pres)
    else:
        value = nextBiggetDiff(pres)
    pres.remove(value)

    if value <= prev:
        if ash:
            a+=value
            ash =False
        else:
            ash = True
            o+=value
        prev = value


print(a-o)