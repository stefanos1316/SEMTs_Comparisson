# Requirements
-java version 8
-maven version 3

# Platform's set-up
Before testing any applications execute the script Disable_P_State if the systems kernel version is higher than 3.10 (use uname -r to find it)

# General Information
This directory contains a number of scripts that will execute an applications and on the site will retrieve energy/power measurements.
The whole process is automated for the users, however, a user has to define as a command line arguments its application's paramenters.

# How to Run
The usual way for executing any of thise scripts follows as:

	$./script.[jolinar, jalen, PowerAPI, RAPL] /path/where/the/jar/file/is/located/ /path/to/store/log/files/and/results/ application's_parameter_1, application's_parameter_2, ... application's_parameter_N

# Optional Information
It is suggested for the user to store the log files and results (command line arguments $2) in the results directory found under the SEMTs_Comparisson repository.

