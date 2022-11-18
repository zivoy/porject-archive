# your mission.
# write a function called collatz
# the function takes one number N.
# if the number N is even, return a number that is N/2
# if the number N is odd return N*3+1
# >>> a= collatz(4)
# >>> a
# 2
# >>> b=collatz(5)
# >>> b
# 16

def collatz(N):
    if N % 2 ==0 : # even
        return N/2
    else: # odd
        return N*3+1
    
# print_all_until_1
# do the collatz until 1
# now change it ,
#so that it doesn't rint ANYTHING 
# >>> c = do_until_1(8)
# >>> c
# 3

#########################3

# def get_max
# is a function that returens the biggest number .



###########################

def do_until_1(t):
    if t < 1:
        return 0
    # print t
    counter = 0
    while t != 1:
        # print t,
        t = collatz(t)
        # print "->",t
        counter  = counter + 1
        
    return counter

def print_until_1(t):
    if t < 1:
        return #0
    # print t
    counter = 0
    while t != 1:
        print t,
        t = collatz(t)
        print "->",t
        counter  = counter + 1
        
   # return counter
    
