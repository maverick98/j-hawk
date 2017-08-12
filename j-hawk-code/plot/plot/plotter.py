import matplotlib.pyplot as plt
from scipy.interpolate import spline
import numpy as np
import dateutil.parser

def date_linspace(start, end, steps):
  delta = (end - start) / steps
  increments = range(0, steps) * np.array([delta]*steps)
  return start + increments

def plotSmoothCurve(file,title,xlabel,ylabel, T , power ):
    xnew = date_linspace(T.min(), T.max(),4)

    #power_smooth = spline(T, power, xnew)
    plt.title(title)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.plot(xnew, power)
    plt.savefig(file, dpi=100)
    plt.show()
    return



T1 = np.array([])
power1 = np.array([])
lines = [line.rstrip('\n') for line in open('perf.log')]
for line in lines:
    page,startTime,endTime,duration = line.split(",")

    yourdate = dateutil.parser.parse(startTime)

    T1 = np.append(T1,yourdate)

    power1=np.append(power1,duration)






plotSmoothCurve("test.png","myTitle","Time","Time Taken in seconds", T1, power1)



