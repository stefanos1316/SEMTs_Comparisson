# SEMTs_Comparisson

This repo contains a number of scripts in order to execute and collect results at different granularities of a system. 
To achieve that, we make use of availabel open source Software Energy Monitoring Tools (SEMTs).
Also, there is a number of different benchmarks and applications, that provide different settings in order to execute them. 
Most of the scripts (found under the script directory) containes information on how to execute them.

For more information or questions you can contact with me at <sgeorgiou@aueb.gr>, <sgeorgiou@singularlogic.eu> or <stefanos1316@gmail.com>

# Cloning
For cloning this repository you should use the receursive command since it includes submodules

$ git clone --recursive https://github.com/stefanos1316/SEMTs_Comparisson.git

In case you used simple clone command, add the submdodules using the following commands inside the cloned repo.

$ git submodule init

$ git submodule update

# Tested on

* Fedora 24, 25
* Ubuntu 14.04

# Using the Platform

In order to install all the tools and to perform the necessary changes for system configurations, we developed the [SetUpPlatform](https://github.com/stefanos1316/SEMTs_Comparisson/tree/master/scripts/SetUpPlatform) scripts. Initially, run the **script.CleanAllDirectories**, then execute the **script.InstallAllTools** (using sudo since is required to enable some modules) to install all the tools, and, finally, run the **script.Disable_P_State** (Jalen and Jolinar are using old kernel modules, thus, is necessary to disable P_state).
  
If all scripts executed corrently, try using the tools by running the scripts.
