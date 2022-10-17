#no year with repeting charecters eg 2012
#find years withwt eg 2013
def year_count():
    start_year = int(raw_input("starting year: "))
    end = int(raw_input("until: "))+1
    print range(10)
    for year in range(start_year, end + 1):
        digits = [0] * 10
        for char in list(str(year)):
            digits[int(char)] += 1
        print digits,
        if max(digits) == 1:
            print year,
        print
