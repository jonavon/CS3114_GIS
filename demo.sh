#!/bin/bash

#Download demo files.
wget 'http://courses.cs.vt.edu/~cs3114/Spring13/barnette/assignments/p4/GIS-DemoFiles.zip';

# Extract jar file.
# Not necessary in the source folder
# Runing the below git command will create a jar archiue
# $> git archive HEAD --format=zip -o jowilcox.Project4.2.jar
# jar -xvf jowilcox.Project4.2.jar;

# Make compile directory.
mkdir bin;

# Compile source files.
find ./src/main/java -name "*.java" | xargs javac -d ./bin

# Create executable jar file.
cd bin/;
jar -cvfe ../GIS.jar edu.vt.jowilcox.cs3114.p4.gis.GIS .;
cd ..;
chmod +x GIS.jar;
ln -v GIS.jar GIS;

# Extract demo files.
unzip  GIS-DemoFiles.zip;
cd GIS-DemoFiles/;

# Run scripts.
time ../GIS {demoDB,DemoScript,jowilcoxLog}01.txt;
time ../GIS {demoDB,DemoScript,jowilcoxLog}02.txt;
time ../GIS {demoDB,DemoScript,jowilcoxLog}03.txt;
time ../GIS {demoDB,DemoScript,jowilcoxLog}04.txt;
time ../GIS {demoDB,DemoScript,jowilcoxLog}05.txt;
time ../GIS {demoDB,DemoScript,jowilcoxLog}06.txt;
time ../GIS {demoDB,DemoScript,jowilcoxLog}07.txt;

 Open Scripts.
\gvim -m --servername "GIS-DEMO" --remote-wait-silent +":vsp jowilcoxLog01.txt" DemoLog01.txt;
\gvim -m --servername "GIS-DEMO" --remote-wait-silent +":vsp jowilcoxLog02.txt" DemoLog02.txt;
\gvim -m --servername "GIS-DEMO" --remote-wait-silent +":vsp jowilcoxLog03.txt" DemoLog03.txt;
\gvim -m --servername "GIS-DEMO" --remote-wait-silent +":vsp jowilcoxLog04.txt" DemoLog04.txt;
\gvim -m --servername "GIS-DEMO" --remote-wait-silent +":vsp jowilcoxLog05.txt" DemoLog05.txt;
\gvim -m --servername "GIS-DEMO" --remote-wait-silent +":vsp jowilcoxLog06.txt" DemoLog06.txt;
\gvim -m --servername "GIS-DEMO" --remote-wait-silent +":vsp jowilcoxLog07.txt" DemoLog07.txt;
cd ..;
