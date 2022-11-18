import random
from matplotlib import pyplot as plt
r = []
for i in range(100):
    r.append(random.randint(0,100))
plt.plot(r)
plt.show()