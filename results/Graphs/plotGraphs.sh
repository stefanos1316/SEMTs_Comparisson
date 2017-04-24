#!/bin/bash

#this script will create the graphs for the user automatically

title="Scalable Load Benchmark Power Measurements with 50 percent core load"
filename="Scalable Load Benchmark Power Measurements.ps"
pdf="Scalable Load Benchmark Power Measurements.pdf"
gnuplot<<EOF
set size 1.0,1.0;
set terminal postscript landscape color "Times-Roman" 14 linewidth 2
set timestamp "%d/%m/%y %H:%M"
set key left top Left noreverse enhanced box linetype -1 linewidth 1.000 sample 4 spacing 1 width 0 height 0 autotitles
set grid back lt 0 lw 1
set xlabel "Duration (in Seconds)"
set ylabel "Power Measurements (in Watts)"


set output "$filename"
set output "$pdf"
set title "$title"
plot 'all_together.txt' using 1:2 title 'WattsUpPro PowerAPI' with linespoints, 'all_together.txt' using 1:3 title 'WattsUpPro Jolinar' with linespoints, 'all_together.txt' using 1:4 title 'PowerAPI' with linespoints, 'all_together.txt' using 1:5 title 'Jolinar' with linespoints, 'all_together.txt' using 1:6 title 'RAPL MSR' with linespoints
EOF

#printPlot="$(gs $filename)"
#eval=$printPlot
#createpdf="$(ps2pdf $filename $pdf)"
#eval=$createpdf

