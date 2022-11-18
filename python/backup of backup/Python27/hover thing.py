import matplotlib.pyplot as plt
import numpy as np
from numpy import genfromtxt
from matplotlib.font_manager import FontProperties
from pylab import rcParams
fontP = FontProperties()
fontP.set_size('small')
def loadData(filename):
    return genfromtxt(filename, delimiter=' ')
def plotData(data):
    rcParams['figure.figsize'] = 10, 6 # Set figure size to 10" wide x 6" tall
    t = data[:,0]
    altitude = data[:,1]
    verticalspeed = data[:,2]
    acceleration = data[:,3] # magnitude of acceleration
    gforce = data[:,4]
    throttle = data[:,5] * 100
    dthrottle_p = data[:,6] * 100
    dthrottle_d = data[:,7] * 100
    # Top subplot, position and velocity up to threshold
    plt.subplot(3, 1, 1)
    plt.plot(t, altitude, t, verticalspeed, t, acceleration)
    plt.axhline(y=100,linewidth=1,alpha=0.5,color='r',linestyle='--',label='goal');
    plt.text(max(t)*0.9, 105, 'Goal Altitude', fontsize=8);
    plt.title('Craft Altitude over Time')
    plt.ylabel('Altitude (meters)')
    plt.legend(['Altitude (m)','Vertical Speed (m/s)', 'Acceleration (m/s^2)'], "best", prop=fontP, frameon=False)
    # Middle subplot, throttle & dthrottle
    plt.subplot(3, 1, 2)
    plt.plot(t, throttle, t, dthrottle_p, t, dthrottle_d)
    plt.legend(['Throttle%','P','D'], "best",  prop=fontP, frameon=False) # Small font, best location
    plt.ylabel('Throttle %')
    # Bottom subplot, gforce
    plt.subplot(3, 1, 3)
    plt.plot(t, gforce)
    plt.axhline(y=1,linewidth=1,alpha=0.5,color='r',linestyle='--',label='goal');
    plt.text(max(t)*0.9, 1.2, 'Goal g-force', fontsize=8);
    plt.legend(['gforce'], "best", bbox_to_anchor=(1.0, 1.0), prop=fontP, frameon = False) # Small font, best location
    plt.xlabel('Time (seconds)')
    plt.ylabel('G-force');
    plt.show();
