#!/bin/bash

#This script will combine our results in a single folder and will add 300 lines with 0.0 watts measurements among 
#the results (it's the delay we set in our scripts before executing them)

parentDir="./power*"
for dirFiles in $parentDir;
do
	echo "First is file $dirFiles"
	while read fileLine; 
	do
		parameter=$(echo $fileLine | awk -F "=" '{print $6}') 
		echo "$parameter" >> results_PowerAPI.txt
	done < $dirFiles

	for i in {1..300};
	do
		echo 0.000 >>results_PowerAPI.txt
	done
	
done

exit;
