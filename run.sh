#!/bin/bash

mvn clean package \
	&& chmod +x ./target/GIS.jar \
	&& ln -vf ./target/test-classes/files/VA_Monterey.txt ./VA_Monterey.txt \
	&& ln -vf ./target/test-classes/files/NM_All.txt ./NM_All.txt \
	&& ln -vf ./target/test-classes/files/CO_All.txt ./CO_All.txt \
	&& time ./target/GIS.jar /tmp/db.txt ./target/test-classes/files/command/Script08.txt /tmp/log.txt;
