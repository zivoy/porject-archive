import random

bombs = 10
width = 9
height = 9

text=False

elements = {
  1: ":one:",
  2: ":two:",
  3: ":three:",
  4: ":four:",
  5: ":five:",
  6: ":six:",
  7: ":six:",
  8: ":eight:",
  0: ":zero:",
  9: ":boom:"
}

textElements = {
  1: "`1`",
  2: "`2`",
  3: "`3`",
  4: "`4`",
  5: "`5`",
  6: "`6`",
  7: "`7`",
  8: "`8`",
  0: "`0`",
  9: "`B`"
}


width=max(1,width)
height=max(1,height)
bombs = min(max(bombs,1),width*height-1)

board = [[0 for j in range(width)] for i in range(height)]

while bombs > 0:
    x = random.randint(0,width-1)
    y = random.randint(0,height-1)
    if board[y][x] == 9:
        continue
    board[y][x] = 9
    bombs -=1

for y in range(height):
    for x in range(width):
        val = board[y][x]
        for i in range(9):
            if val == 9:
                break
            if i == 4:
                continue
            dy = i//3-1
            dx = i%3-1
            tx = x+dx
            ty = y+dy
            if ty < 0 or height <= ty or tx < 0 or width <= tx:
                continue
            if board[ty][tx] == 9:
                val += 1
        if not text:
            print(f"||{elements[val]}||",end="")
        else:
            print(f"||{textElements[val]}||",end="")
        
    print()
