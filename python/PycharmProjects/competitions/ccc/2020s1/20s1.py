times = sorted([tuple(map(int, input().split())) for i in range(int(input()))])

avg=list()
for i in range(len(times)-1):
    this = times[i]
    nxt = times[i+1]
    avg.append(abs((nxt[1]-this[1])/(nxt[0]-this[0])))
print(max(avg))
