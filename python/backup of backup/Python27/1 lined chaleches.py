def pythagars_thyrum():
    import math; print("side 3 = "+str(math.sqrt(int(input("side 1: "))**2 + int(input("side 2: "))**2)))

def play():
    from time import localtime; name = raw_input("play with: "); end_time = int(filter(str.isdigit, raw_input("till: ")))+1200;
    while(int(str(localtime().tm_hour) + str(localtime().tm_min)) < end_time):
        print(name+" play")
