light = 299792458

N, K = map(int, input().split())

line2 = input().split()

times15 = list()
for i in range(K*2):
    number = input().split(".")
    number15 = f"{number[0]}{number[1][:15]}.{number[15:]}"
    times15.append(number15)

speeds = sorted(list(map(lambda x: x*light, times15)))


