#!/bin/bash

#This script will generate a file with random characters content that will be used to the compression app.
#Provide $1 which is the number of charcters
eval=$(cat /dev/urandom | tr -dc 'a-zA-Z0-9' | head -c $1 > randomFile.txt)
sizeOf=$(ls -l randomFile.txt | awk '{print $5}')
eval=$sizeOf
echo "File randomFile.txt created with size of $sizeOf"
