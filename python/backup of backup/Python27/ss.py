# 4,5
# 4,2
# @ @ @ @ @
# @ @ @ x @
# @ @ @ @ @
# @ @ @ @ @
def ss():
    print "a"
    a=int(raw_input())
    print "b"
    b=int(raw_input())
    print "y"
    y=int(raw_input())
    print "x"
    x=int(raw_input())

    for i in range (a):
        for j in range(b):
            if j==x and i==y:
                print "#",
            else:   
                print "@",
        print


def XO():
    board = [["-","-","-"],["-","-","-"],["-","-","-"]]
    printboard(board)
    for aye in range(5):
        try :
            movePlayer("X",board)

            movePlayer("O",board)
        except:
            print "game over"
            break
    print "out of loop"
def movePlayer(player,board):
    
    print player
    print "x? "
    x= int(raw_input())-1
    print "y? "
    y = int(raw_input())-1
    board[x][y]= player
    printboard(board)
    

def printboard(board):
    for i in board:
        for j in i:
            print j,
        print
