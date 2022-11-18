def sayhi():
    e=0
    l=0
    t=" times"

    l=int(raw_input("how meny time do i print? "))

    if l == 1:
        t=" time"
    
    print "i will print hi "+str(l)+t+"\r"
    
    while e < l:
        print "hi"
        e+=1

