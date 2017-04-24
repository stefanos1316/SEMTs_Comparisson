#!/bin/bash 

for i in {1..7566}; 
do
	echo $i >> time_watts_only.txt
done

echo "Done!!! Exiting!!!"
exit
