#!/bin/bash

#This script will combine our results in a single folder and will add 300 lines with 0.0 watts measurements among 
#the results (it's the delay we set in our scripts before executing them)

count=1
parentDir="./*power*"
for dirFiles in $parentDir;
do
	
	while read fileLine; 
	do
		parameter=$(echo $fileLine | awk -F ";" '{print $1}')

		echo "$parameter" >> Jolinar_results_PowerAPI.txt
		if [ $count == 550 ]; 
		then 
		break;
		fi	
		count=$(($count + 1))
	done < $dirFiles

	for i in {1..300};
	do
		echo 0.000 >>Jolinar_results_PowerAPI.txt
	done
	count=1
done

exit;
