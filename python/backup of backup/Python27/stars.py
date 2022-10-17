from __future__ import print_function

class Star(object):
    def __init__(self,count):
        self.count = count

    def start(self):
        for i in range(1,self.count):
            for j in range (i): 
                print('*', end='') # PEP 3105: print As a Function 
            print()

a = Star(int(input("input the amunot of stars: "))+1)
a.start()
