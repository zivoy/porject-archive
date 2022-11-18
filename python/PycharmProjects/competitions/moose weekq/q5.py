from functools import lru_cache


@lru_cache(maxsize=int(1e5))
def caterpillar(x, K, M):
    if K == 0:
        return x
    element = x - (K * M)
    return element * caterpillar(x, K - 1, M)


def executeSum(n, k, m):
    d = 0
    for i in range(k * m, n + 1):
        d += caterpillar(i, k - 1, m)
    return d


[print(executeSum(*map(int, input().split())) % 998244353) for i in range(int(input()))]
