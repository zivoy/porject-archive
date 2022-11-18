import turtle
scr = turtle.Screen()
koch = turtle.Turtle()
theeeeeePatern=[0,1,0]
start =650
sidesInATriangle=3

def kochLoj(itr):
    pattern=[1]
    temp=[]
    final=[]
    for i in range(itr):
        for j in pattern:
            temp.extend(theeeeeePatern)
            temp.extend(str(j))
            temp=list(map(int,temp))
        pattern=temp
        temp=[]
    pattern=list(map(bool,pattern))
    final.extend(pattern)
    final.extend(pattern)
    final.extend(pattern)
    return final

def kochSnowflake(itr):
    initProgram()
    sides=3*4**itr
    sideLength=start*3**-itr
    turn=kochLoj(itr)
    for i in turn:
        koch.forward(sideLength)
        if i:
            koch.right(120)
        else:
            koch.left(60)
    scr.exitonclick()

def initProgram():
    scr.reset()
    koch.hideturtle()
    koch.penup()
    koch.left(90)
    koch.forward(start/1.75)
    koch.right(150)
    koch.pendown()
    koch.speed(500)


kochSnowflake(5)
