def pals(string):
    lst = dict()
    maximum=0
    for cut in range(len(string)+1):
        for pos in range(len(string)-cut+1):
            prt=string[pos:cut+pos]
            if prt == prt[::-1]:
                if prt not in lst:
                    lst[prt]=0
                lst[prt] += 1
        if lst:
            maximum = max(max([len(k)*v for k,v in lst.items()]),maximum)
        lst = dict()
    return maximum


print(pals(input()))
