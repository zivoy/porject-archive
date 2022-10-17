def is_prime(a):
    return all(a % i for i in xrange(2, a))
breakcount =0
for i in range(1009,1423):#1319, 1423
    if (is_prime(i) == False):
        continue
    if breakcount > 10:
        break
    for j in range(1009,7027 +1):#7577, 7027
        if (is_prime(j) == False):
            continue
        if (((i*j)%10000000)/1000000==0 or ((i*j)/10000000)>0):
            print "break"+" "+str(i)+" "+str(j)
            breakcount +=1
            continue
        print str(i*j)+" "+str(i)+" "+str(j)
        if breakcount > 10:
            break
