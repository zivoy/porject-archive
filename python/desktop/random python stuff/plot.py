import matplotlib.pyplot as plt

# set up the figure
fig = plt.figure()
ax = fig.add_subplot(111)
ax.set_xlim(0,10)
ax.set_ylim(0,10)

# draw lines
xmin = 1
xmax = 10
y = 5
height = 0.5

plt.hlines(y, xmin, xmax)
plt.vlines(xmin, y - height / 2., y + height / 2.)
plt.vlines(xmax, y - height / 2., y + height / 2.)

# draw a point on the line
px = 7
plt.plot(px,y, 'ro', ms = 15, mfc = 'r')

pz = 4
plt.plot(pz,y, 'ro', ms = 10, mfc = 'b')

# add numbers
plt.text(xmin - 0.1, y, '0', horizontalalignment='right')
plt.text(xmax + 0.1, y, '100', horizontalalignment='left')

plt.axis('off')
plt.show()
