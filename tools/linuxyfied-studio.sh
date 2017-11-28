#!/bin/sh

echo "We are deleting all of logs of studio tool."
rm -rf logs/* 
echo "We are deleting all of DMD temp files."
rm -rf ffhome/sql/*
echo "Cleaning done!"

JAVA_HOME="/usr/lib/jvm/default-java"
CLASSPATH=".:$JAVA_HOME/lib/tools.jar:$CLASSPATH"


classpath_append()
{
	for f in $1
	do 
		echo "Adding $f"
		if [ -z ${f+x} ]
		then 
			echo "JAR file is null."
			continue
		else 
			echo "Appending '$f' to classpath variable"
			export CLASSPATH="$CLASSPATH:./$f"
		fi
	done
}

classpath_append "libs/client/*.jar"
classpath_append "libs/dmd/*.jar"
classpath_append "libs/framework/*.jar"
classpath_append "libs/framework.lib/*.jar"

echo "JAVA_HOME is set to '$JAVA_HOME'"
echo "CLASSPATH is set to '$CLASSPATH'"
$JAVA_HOME/bin/java -DFF_HOME=ffhome -DDMD_HOME=ffhome -Dmode=remote-workflow-datamodel-organization com.bull.flexflow.studio.MainFrame