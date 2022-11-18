_, K = map(int, input().split())
items = list(map(int, input().split()))


def be(item1, item2):
    if item2 is None:
        return False
    return item1 >= item2


def bt(item1, item2):
    if item2 is None:
        return False
    return item1 > item2


score = 0
for i in range(K):
    N = len(items) - 1
    a = [items[N], N]
    d = [0, None, None]
    for j in range(1, N + 1):
        if bt(items[N - j], a[0]):
            a = [items[N - j], N - j]
        else:
            if be(a[0] - items[N - j], d[0]):
                d = [a[0] - items[N - j], N - j, a[1]]
    score += d[0]
    if d[0] == 0:
        break
    items.pop(d[1])
    items.pop(d[2])
print(score)