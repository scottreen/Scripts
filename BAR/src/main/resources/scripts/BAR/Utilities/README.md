# Utilities

Productivity tools.

## Commander
BAR Commander is a neat keyboard-based file browser for ImageJ that can be triggered
application-wide by pressing <kbd>F1</kbd>.
Features include: drag-and-drop support, interaction with native file manager, regex
filtering, built-in console for common operations, and ability to produces filtered lists
of directory contents ([Documentation page](http://imagej.net/BAR#Commander)).

![commander overview](../../../../../../../images/commander-overview.png)


## Create Toolset
Creates toolbar menus for running plugins, macros and scripts. It can also group built-in
tools (such as drawing tools) in custom toolsets. It is part of ImageJ1 distributions
(since IJ 1.41).


## Productivity Menus
Appends Menus to the IJ toolbar, including:

1. **Calibration Menu Tool**: Provides shortcuts for spatial calibration of images lacking
   metadata.

1. **List Folder Menu Tool**: Provides a drop-down-list of the contents of a specified
   directory that is remembered across restarts.
   Offers commands to reveal directories in the native file browser and commands to list
   directory contents to dedicated windows.

1. **Shortcuts Menu Tool**: Lists the user's most used commands. List is remembered across
   restarts.


## List BAR scripts
Produces a list of all BAR scripts (including those not registered in the `BAR>` menu.


## ROI Manager Tools
Installs ROI Manager Tools, a toolbar that renames selections stored in the ROI Manager.
More information can be found on its initial
[documentation page](http://imagej.net/plugins/roi-manager-tools), that has yet to be
transferred to [imagej.net](http://imagej.net/).


## New Snippet
A Java plugin implementing a convenient way to generate new scripts and access
[boilerplate] code that generates functions and methods that simplify the access to [lib]
files. More details on the [documentation page](http://imagej.net/BAR#Snippets).

![snippet creator](../../../../../../../images/snippet-creator.png)


[boilerplate]: https://github.com/tferr/Scripts/tree/master/BAR/src/main/resources/boilerplate



------
| [Home] | [Analysis] | [Annotation] | [Data Analysis] | [lib] | [My Routines] | [Segmentation] | [Tools] | [Utilities] | [Wiki] |

[Home]: https://github.com/tferr/Scripts
[Analysis]: https://github.com/tferr/Scripts/tree/master/BAR/src/main/resources/scripts/BAR/Analysis
[Annotation]: https://github.com/tferr/Scripts/tree/master/BAR/src/main/resources/scripts/BAR/Annotation
[Data Analysis]: https://github.com/tferr/Scripts/tree/master/BAR/src/main/resources/scripts/BAR/Data_Analysis
[lib]: https://github.com/tferr/Scripts/tree/master/BAR/src/main/resources/lib
[My Routines]: https://github.com/tferr/Scripts/tree/master/BAR/src/main/resources/scripts/BAR/My_Routines
[Segmentation]: https://github.com/tferr/Scripts/tree/master/BAR/src/main/resources/scripts/BAR/Segmentation
[Tools]: https://github.com/tferr/Scripts/tree/master/BAR/src/main/resources/tools
[Utilities]: https://github.com/tferr/Scripts/tree/master/BAR/src/main/resources/scripts/BAR/Utilities
[Wiki]: https://imagej.net/BAR

