import matplotlib.pyplot as plt
from scipy.interpolate import spline
import numpy as np
import dateutil.parser

def date_linspace(start, end, steps):
  delta = (end - start) / steps
  increments = range(0, steps) * np.array([delta]*steps)
  return start + increments

def plotSmoothCurve(file,title,xlabel,ylabel, T , power ):
    xnew = date_linspace(T.min(), T.max(),len(T))


    #power_smooth = spline(T, power, xnew)
    plt.title(title)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.plot(xnew, power)
    plt.savefig(file, dpi=100)
    plt.show()
    return


def parseAndPlot(outPage,lines):
    xSeries = np.array([])
    ySeries = np.array([])

    for line in lines:
        page, startTime, duration = line.split(",")

        yourdate = dateutil.parser.parse(startTime)

        xSeries = np.append(xSeries, yourdate)

        ySeries = np.append(ySeries, duration)

    plotSmoothCurve(outPage, outPage, "Time", "Time Taken in seconds", xSeries, ySeries)
    return


lines = [line.rstrip('\n') for line in open('perf.log')]

map = {}

for line in lines:
    page, startTime, duration = line.split(",")
    subPages= page.split("-")
    currentPage=""
    for i  in range(len(subPages)):
        if currentPage:
            currentPage += "-"+subPages[i]
        else:
            currentPage +=subPages[i]
        newLines = map.get(currentPage)
        if newLines is None:
            newLines = np.array([])
            newLines=np.append(newLines,line)
            map[currentPage] = newLines
        else:
            newLines=np.append(newLines,line)
            map[currentPage]= newLines

for page in map:
    lines = map[page]
    parseAndPlot(page,lines)











