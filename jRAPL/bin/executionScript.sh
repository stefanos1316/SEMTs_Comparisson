#!/bin/bash

#This is a script which is going to execute the EnergyCheckUtils.java by testing it with different parameters
#because I am to lazy to execute them one-by-one

echo "Measuring power consumption for Energy Code Smells"

javac EnergyCheckUtils.java
java EnergyCheckUtils InLineMethod 0 8192
java EnergyCheckUtils InLineMethod 1 8192

echo "Execution finished."
