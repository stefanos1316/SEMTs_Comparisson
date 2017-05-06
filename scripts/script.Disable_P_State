#!/bin/bash


# This script will disable the p_state for kernels older than 3.10 version

echo "Note: This script will work only for Fedora distro"

firstNumber=$(uname -r | awk -F "." '{print $1}')
secondNumber=$(uname -r | awk -F "." '{print $2}')

if [ $firstNumber -ne 3 ];
then
	echo "Not equal to kernel version 3"
		
		#Check if path exist and disable it
		echo GRUB_CMDLINE_LINUX=\"intel_pstate=disable\" >> /etc/default/grub
		eval=$(grub2-mkconfig -o /boot/grub2/grub.cfg)
		eval=$(grub2-mkconfig -o /boot/efi/EFI/fedora/grub.cfg)
fi

echo "The system will reboot in order to apply changes..."
sleep 5

eval=$(reboot)

