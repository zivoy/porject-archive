# -*- coding: cp1252 -*-
#Recamán's Sequence
n=0
h=100
m=[n]

o=n

for i in range(1,h+1):
    #backwords
    if n-i not in m and n-i >0:
        n=n-i
        m.append(n)
    else:
        n=n+i
        m.append(n)
    #print i,n


'''
l=[0]

for n in xrange(1, 101):

    x=l[n - 1] - n

    if x>0 and not x in l: l+=[x, ]

    else: l+=[l[n - 1] + n]
'''
s=sorted(m)
g=[]
for j in range(s[-1]+1):
    if j not in m:
        g.append(j)

d=[]

for a in m:
    if a == o:
        continue
    elif a>o:
        d.append(True)
    elif a<o:
        d.append(False)
    o=a

'''import matplotlib.pyplot as plt
import pandas as pd

df=pd.DataFrame({'x': range(1,s[-1]+1), 'y1': m, 'y2': g})
m=map(int, m)
g=map(int, g)
def plot():
    plt.plot( 'x', 'y1', data=df, marker='o', markerfacecolor='blue', markersize=12, color='skyblue', linewidth=4, label="Sequence")
    plt.plot( 'x', 'y2', data=df, marker='', color='olive', linewidth=2, label="not included")
    #plt.plot( 'x', 'y3', data=df, marker='', color='olive', linewidth=2, linestyle='dashed', label="toto")
    plt.legend()'''

import matplotlib.pyplot as plt

'''# set up the figure
fig = plt.figure()
ax = fig.add_subplot(111)
ax.set_xlim(0,s[-1]+1)
ax.set_ylim(0,10)

# draw lines
xmin = 0
xmax = s[-1]+1
y = 5
height = 1

plt.hlines(y, xmin, xmax)
plt.vlines(xmin, y - height / 2., y + height / 2.)
plt.vlines(xmax, y - height / 2., y + height / 2.)

# draw a point on the line
for b in m:
    px = b
    plt.plot(px,y, 'ro', ms = 5, mfc = 'k')


# add numbers
plt.text(xmin - 0.1, y, '0', horizontalalignment='right')
plt.text(xmax + 0.1, y, str(s[-1]+1), horizontalalignment='left')

plt.axis('off')'''

x = range(h+1)
y = m
colors = 'k'
area = 10
plt.scatter(x, y, s=area, c=colors, alpha=1,zorder=2)
plt.plot(x,y,zorder=0)
plt.grid()
#plt.plot( x, y, marker='o', markerfacecolor='blue', markersize=area, color='skyblue', linewidth=area/3)


if raw_input("print? (n/y)") == "y":
    print m
    print
    print d
    print
    print s
    print
    print g
    plt.show()
