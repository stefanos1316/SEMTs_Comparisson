# Software Energy Profilers

In this repository, we try to collect and customize (when it is necessary) available Software Energy Profiling (SEP) tools and utilities.  
We aim to provide a collection of SEP that can measure energy dissipation of a software at different granularities of the system. 
In addition, we customized some of the tools by adding different schafoldings to make the easy of use for the practitioners.
Also, there is a number of different benchmarks and applications customized with different settings that can be used as a test case. 

For more information or questions you can contact with me at <sgeorgiou@aueb.gr>, <sgeorgiou@singularlogic.eu> or <stefanos1316@gmail.com>.


# Requirements

* [feedGnuplot](http://search.cpan.org/~dkogan/feedgnuplot-1.44/bin/feedgnuplot), used v. 1.42


# Cloning

For cloning this repository you should use the receursive command since it includes submodules

      $ git clone --recursive https://github.com/stefanos1316/SEMTs_Comparisson.git

In case you used simple clone command, add the submdodules using the following commands inside the cloned repo.

      $ git submodule init

      $ git submodule update


# Distros tested on

* Fedora 24, 25
* Ubuntu 14.04


# Using the platform

In order to install all the tools and to perform the necessary changes for system configurations, we developed the [installTools.sh](https://github.com/stefanos1316/SEMTs_Comparisson/tree/master/scripts/SetUpPlatform) scripts. 
Initially, run the **clearDirs.sh**, then execute the **installTools.sh** (using sudo since is required to enable some modules) to install all the tools, and, finally, run the **disablePstate.sh** (Jalen and Jolinar are using old kernel modules, thus, is necessary to disable P_state).
 
If all scripts executed corrently, you may use the tools after installing the necessary components such as Gnuplot.
Use -h or --help, command line arguments, to receive an informative description of the tool's features.

# License


