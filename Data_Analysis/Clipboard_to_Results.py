# Clipboard_to_Results.py
# IJ BAR: https://github.com/tferr/Scripts#scripts
#
# Imports numeric values copied to the clipboard into the Results table. Useful, since
# BARs that analyze tabular data can only read values from the main IJ "Results" table
#
# NB: When copying data from withing IJ (e.g., lists from histograms or plot profiles),
# Use Edit>Options>Input/Output... to specify if column headers/row numbers should be
# copied to the clipboard


import os
from ij import IJ
from ij.plugin.filter import Analyzer
import ij.measure.ResultsTable as RT


def getClipboard():
    """Tries to extract text from the system clipboard"""
    from java.awt.datatransfer import DataFlavor
    from java.awt import Toolkit

    clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()
    contents = clipboard.getContents(None)
    if (contents!=None) and contents.isDataFlavorSupported(DataFlavor.stringFlavor):
        return contents.getTransferData(DataFlavor.stringFlavor)
    else:
        return ""


s = getClipboard()
try:
    path = IJ.getDirectory("temp") +"IJclipboardTable.csv"
    rtFile = open(path, "w")
    rtFile.write(s)
    rtFile.close()
    rt = RT.open(path) #IOException if getClipboard()==""
    if Analyzer.resetCounter():
        rt.show("Results")
except:
    IJ.error("Could not place clipboard into Results table.")
