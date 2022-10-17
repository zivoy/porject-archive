from random import random
smallest = 15#0.000000000109
while True:
	a = random()
	if a<smallest:
		print '%.12f' % a +" "+'%.12f' % (a*15)
		smallest = a
