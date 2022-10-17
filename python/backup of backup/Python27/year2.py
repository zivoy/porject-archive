#no year with repeting charecters eg 2012
#find years withwt eg 2013
def year_count():
    year = int(raw_input("starting year: "))
    end = int(raw_input("until: "))+1
    print ("Started at "+str(year))
    while True:
        charecters = 10**int(len(str(year)))
        numbers = []
        repeted = False
        while charecters > 1:
            digit = year%charecters/(charecters/10)
            if digit not in numbers:
                numbers.append(digit)
            else:
                repeted = True
            charecters /= 10
        if repeted == False: print year
        year+=1
        if year == end:
            print ("Stoped at "+str(year-1))
            break
