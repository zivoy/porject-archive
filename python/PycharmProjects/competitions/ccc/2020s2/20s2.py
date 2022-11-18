from collections import deque


def factor(number, r=1000, c=1000):
    for i in range(1, number+1):
        num = number/i
        if not num % 1:
            if i <= r and num <= c:
                yield i, int(num)


m = int(input())
n = int(input())
grid = [list(map(int, input().split())) for i in range(m)]

end = (m-1, n-1)
queue = deque()
visited = list()
queue.append([0, 0])
found = False
while bool(queue):
    if bool(queue):
        pos = queue.pop()
        visited.append(pos)
        if tuple(pos) == end:
            found = True
            break
        for i in factor(grid[pos[0]][pos[1]], m, n):
            cord = [i[0]-1, i[1]-1]
            if cord in visited:
                continue
            queue.append(cord)

print("yes" if found else "no")
