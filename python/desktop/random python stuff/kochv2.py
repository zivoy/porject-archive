import turtle
scr= turtle.Screen()
koch= turtle.Turtle()
koch.ht()
koch.speed(0)

def edge(n=0,size=250):
    if n==0:
        koch.forward(size)
    else:
        edge(n-1,size/3)
        koch.left(60)
        edge(n-1,size/3)
        koch.right(120)
        edge(n-1,size/3)
        koch.left(60)
        edge(n-1,size/3)
        
def snowflake(n=0,size=250):
    koch.penup()
    koch.left(90)
    koch.forward(size/2)
    koch.right(150)
    koch.pendown()
    
    edge(n+1,size)
    koch.right(120)
    edge(n,size)
    koch.right(120)
    edge(n-1,size)
    ''' edge(n,size)
    koch.right(90)
    edge(n+1,size)
    koch.right(90)
    edge(n+2,size)
    koch.right(90)
    edge(n+3,size)'''
    
snowflake(3,650)
scr.exitonclick()
