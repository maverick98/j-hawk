import matplotlib.pyplot as plt
from scipy.interpolate import spline
import numpy as np
def plotSmoothCurve(file,title,xlabel,ylabel, T , power ):
    xnew = np.linspace(T.min(), T.max(), 300)
    power_smooth = spline(T, power, xnew)
    plt.title(title)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.plot(xnew, power_smooth)
    plt.savefig(file, dpi=100)
    plt.show()
    return

T = np.array([6, 7, 8, 9, 10, 11, 12])
power = np.array([1.53E+03, 5.92E+02, 2.04E+02, 7.24E+01, 2.72E+01, 1.10E+01, 4.70E+00])

plotSmoothCurve("test.png","myTitle","Time","Time Taken in seconds", T, power)



