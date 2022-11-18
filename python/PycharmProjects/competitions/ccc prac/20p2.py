n=int(input())
s=list(map(int,input().split()))
r=list(map(int,input().split()))

sw=0
rw=0
sl=list()
rl=list()
for i in range(n):
    sw+=s[i]
    rw+=r[i]
    sl.append(sw)
    rl.append(rw)
ans=0
for i in range(n):
    idx = n-i-1
    if sl[idx] == rl[idx]:
        ans = idx+1
        break

print(ans)
