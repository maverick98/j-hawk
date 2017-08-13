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
    plt.plot(T, power)
    plt.savefig(file, dpi=100)
    plt.show()
    return

#It is expected all the pages in these lines should be same
def parseAndPlot(lines):
    xSeries = np.array([])
    ySeries = np.array([])
    imgFile="dummy"
    for line in lines:
        page, startTime, duration = line.split(",")
        imgFile = page
        yourdate = dateutil.parser.parse(startTime)

        xSeries = np.append(xSeries, yourdate)

        ySeries = np.append(ySeries, duration)

    plotSmoothCurve(imgFile, imgFile, "Time", "Time Taken in seconds", xSeries, ySeries)
    return


lines = [line.rstrip('\n') for line in open('perf.log')]
#parseAndPlot(lines)
map = {}

for line in lines:
    page, startTime, duration = line.split(",")
    subPages= page.split("-")
    currentPage=""
    for i  in range(len(subPages)):
        currentPage += "-"+subPages[i]
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
    parseAndPlot(lines)










