#!/bin/bash
# Bumps versions in a specific set of files.  Does commit after.
#

#set -x

usage(){
   			echo " "
   			echo " "
   			echo " "
   			echo "Usage:   $0 [ old-version, new-version ]"
   			echo " "
   			echo " "
   			echo "  Example:"
   			echo "     $0  2.8.4-RELEASE, 2.8.6-RELEASE  -Executes find replace on 8.4 to 8.6 in fileset."
   			echo " "
   			echo " Run in: /spring-cloud-petclinic/projects/sh folder! "
   			echo " "
   			echo " "   			   			
	exit 1
}


 	if [ $# -lt 1 ]
 		then
			usage
 	fi
   	
	if [ "$1" == "--Help" ] ||  [ "$1" == "--help" ] || [  "$1" == "--H" ] || [ "$1" == "--h" ] || [  "$1" == "-H" ] || [ "$1" == "-h" ]
	  then
	    usage
	fi
 
 	
 	if [ $# -lt 2 ]
 		then
			usage
 	fi
 	
  oldVersion=
  newVersion=
  
  oldVersion="$1"
  newVersion="$2"
 
  # Start in /spring-cloud-petclinic/projects/sh folder!
 
	fileSet=("../spring-cloud-petclinic-service/pom.xml" "../spring-cloud-petclinic-service/.envrc" ) 

	cleanUpfileSet=("../spring-cloud-petclinic-service/pom.xml-e" "../spring-cloud-petclinic-service/.envrc-e" ) 
 
 	echo ""
 	echo "OldVersion: $oldVersion"
 	echo "NewVersion: $newVersion"
 	
 	echo ""
 	
 	
	for theFile in "${fileSet[@]}"
	do
	  : 
	  echo "Processing File: $theFile"
	   
	  # find/replace oldVersion to newVersion
	   

	  # use grep to determine if old string exists.  If not, blow out.
		 
	  grep -q $oldVersion $theFile
		returnCode=$?	   
	   
	  if [ $returnCode -ne 0 ]
	  	then
	  	
	  	echo "*** ERROR Occured Processing File: $theFile!  Cannot Find: $oldVersion"
	  	break;
	  
	  else
	  
	  	sed -i -e "s/$oldVersion/$newVersion/g " $theFile
	  	
	  fi
	   
	   
	done
	
  if [ $returnCode -eq 0 ]
  	then
  

			# CleanUp Left-Overs	
			for theFile in "${cleanUpfileSet[@]}"
			do
			  : 
			  echo "Removing File: $theFile"
			   
			  rm $theFile
			   
			done
			
			# Jump up 2 levels..
			
			cd ..
			cd ..
			
			pwd
			
			git status
			
			git add .
			
			git commit -m "Bump Version: $oldVersion To: $newVersion"
	
  		git push

	fi

  





