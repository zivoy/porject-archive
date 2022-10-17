#no year with repeting charecters eg 2012
#find years withwt eg 2013
def year_count():
    y = int(raw_input("starting year: "))
    end = int(raw_input("until: "))+1
    while True:
        ones = y%10
        tens = y%100/10
        hundreds = y%1000/100
        thosends = y%10000/1000
        #print (ones)
        #print (tens)
        #print (hundreds)
        #print (thosends)
        x = False
        if ones == tens: x = True
        elif ones == hundreds: x = True
        elif ones == thosends: x = True

        elif tens == hundreds: x = True
        elif tens == thosends: x = True

        elif hundreds == thosends: x = True
        if x != True:
            print (y)
        y+=1
        if y == end:
            print ("stoped at "+str(y-1))
            break
