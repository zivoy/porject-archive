readings = [int(input()) for _ in range(int(input()))]
count=list()


def search(ls,item,idx=1):
    return filter(lambda x: x[idx]==item, ls)


for i in readings:
    if not any(search(count, i,1)):
        count.append((readings.count(i), i))
count=sorted(count)
first = count[-1]
bigst = list(search(count[:-1],count[-2][0],0))
ss=list()
for i in bigst:
    ss.append(abs(first[1]-i[1]))
print(max(ss))

"""
5
1
1
1
4
3

4
10
6
1
8
"""
