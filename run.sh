#!/bin/bash

mvn clean package \
	&& chmod +x ./target/GIS.jar \
	&& ln -v ./target/test-classes/files/VA_Monterey.txt ./target/VA_Monterey.txt \
	&& ./target/GIS.jar /tmp/db.txt ./target/test-classes/files/command/Script01.txt /tmp/log.txt;
