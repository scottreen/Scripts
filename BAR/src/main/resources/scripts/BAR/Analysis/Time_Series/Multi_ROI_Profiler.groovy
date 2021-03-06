//@String(value="This script plots the mean intensity of ROIs (of any type) over time.", visibility="MESSAGE") info
//@Integer(label="Channel",min=1,max=10,style="scroll bar", description="With multichannel images only intensities from this channel are plotted", value="1") channel
//@Boolean(label="Plot mean ± SE of all ROIs", value=false) plotMean
//@Boolean(label="Average Z-dimension in time series", description="<html>If unchecked, only the current Z-position of the time-lapse is used.<br>Ignored when timelapse has no Z-dimension", value=true) averageZ
//@Boolean(label="Use unique colors", description="If unchecked, each ROI series is plotted in gray", value=false) uniqueColors
//@String(value=" ", visibility="MESSAGE") spacer
//@String(label="ROIs source", choices={"ROI Manager", "Image Overlay"}) source
//@String(label="Filter by name", value=" ", description="<html>Only ROIs containing this string will be considered.<br>Leave blank to consider all ROIs.") filter

//@ImagePlus imp
//@UIService uiservice
//@StatusService statusService

/*
 * Multi_ROI_Profiler.groovy
 * IJ BAR: https://github.com/tferr/Scripts#scripts
 *
 * Groovy script that plots ROI intensities over time. Extends ROI Manager's
 * Multi Plot command to multichannel images and ROIs of any type.
 */

import bar.Utils
import ij.gui.Overlay;
import ij.gui.Plot;
import ij.measure.Measurements;
import ij.process.ImageStatistics;
import ij.plugin.frame.RoiManager;
import org.scijava.util.Colors;
import org.scijava.ui.awt.AWTColors;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;


def loadLib(resourcePath) {
	url = Utils.getBARresource(resourcePath)
	gcl = new GroovyClassLoader()
	cls = gcl.parseClass(new GroovyCodeSource(url))
	(GroovyObject) cls.newInstance()
}

def getUniqueColors(n) {
    def rgbColors = Colors.values().toArray()
    def random = new Random()
    def colors = []
    for (i in 0..n) {
        def idx = (i < rgbColors.length) ? i : random.nextInt(rgbColors.length)
        colors.add(AWTColors.getColor(rgbColors[idx]))
    }
    return colors
}

def getROIMeanAcrossTime(roi) {
    def stack = imp.getStack()
    def values = []
    for (frame in 1..frames) {
        def dStats = new DescriptiveStatistics()
        for (slice in zStart..zEnd) {
            def index = imp.getStackIndex(channel, slice, frame)
            def ip = stack.getProcessor(index)
            ip.setRoi(roi)
            def ipStats = ImageStatistics.getStatistics(ip, Measurements.MEAN, cal)
            dStats.addValue(ipStats.mean)
        }
        values.add(dStats.getMean())
    }
    return values
}

def plotAverageOfAllSeries(plot) {
    def rt = plot.getResultsTable()
    if (rt == null)
        return
    def avg = []
    def std = []
    for (row in 0..<rt.getCounter()) {
        def dStats = new DescriptiveStatistics()
        for (col in 1..rt.getLastColumn())
            dStats.addValue(rt.getValueAsDouble(col, row))
        avg.add(dStats.getMean())
        std.add(dStats.getStandardDeviation()/Math.sqrt(dStats.getN()))
    }
    plot.setColor("cyan", "blue")
    plot.addPoints(rt.getColumnAsDoubles(0), avg as double[], Plot.CONNECTED_CIRCLES)
    plot.addErrorBars(std as double[])
}

def getROIs(source, filterString) {
    def rois = []
    switch (source) {
        case ~/.*[Oo]verlay$/:
            overlay = imp.getOverlay()
            if (overlay != null && overlay.size() > 0)
                rois = overlay.toArray()
            break
        default:
            def rm = RoiManager.getInstance()
            if (rm!=null && rm.getCount() > 0)
                rois = rm.getRoisAsArray()
            break
    }
    if (filterString && !filterString.trim().isEmpty()) {
        def excludedROIs = []
        rois.each {
            name = it.getName()
            if (name!= null && !name.contains(filterString))
                excludedROIs.add(it)
        }
        rois = rois - excludedROIs
    }
    return rois
}


rois = getROIs(source, filter)
if (!rois) {
    uiservice.showDialog("No ROIs in $source.\n(Or no matches for the specified filter '$filter')",
    	"No ROIs Available")
    return
}

utils = loadLib("shared/Utils.groovy")
if (!utils.isTimeSeq(imp, uiservice))
	return

statusService.showStatus("Plotting ROIs...")

frames = imp.getNFrames()
zStart = (averageZ) ? 1 : imp.getZ()
zEnd = (averageZ) ? imp.getNSlices() : imp.getZ()
cal = imp.getCalibration()
xvalues = (1..frames) as float[]

def plot = new Plot("ROI Profiles " + imp.getTitle()
                + " Ch$channel", "Time (frame n.)", "Mean")
def colors = (uniqueColors) ? getUniqueColors(rois.size())
                : [].withDefault {AWTColors.getColor(Colors.GRAY)}

rois.eachWithIndex { roi, idx ->
    def name = roi.getName()
    plot.setColor(colors[idx])
    plot.addPoints(xvalues, getROIMeanAcrossTime(roi) as float[], null,
            Plot.LINE, name)
}

if (plotMean)
    plotAverageOfAllSeries(plot)
plot.setLimitsToFit(true)
plot.show()
