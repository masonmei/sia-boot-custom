#!/bin/bash

WORK_DIR=`dirname $0`
WORK_DIR=`cd ${WORK_DIR}; pwd`
echo "Work dir: ${WORK_DIR}"

echo "Setting up environment variables ..."

MAVEN_HOME=${M2_HOME}
if [ ! -d "${MAVEN_HOME}" ]; then
    MAVEN_HOME=${BUILD_KIT_PATH}/maven/apache-maven-3.2.5
fi

JAVA_HOME=${JAVA_HOME}
if [ ! -d "${JAVA_HOME}" ]; then
    JAVA_HOME=${BUILD_KIT_PATH}/java/jdk-1.8-8u20
fi

export MAVEN_HOME && export JAVA_HOME
export PATH=${MAVEN_HOME}/bin:${JAVA_HOME}/bin:${PATH}

echo "Running maven ..."
if [ $# -eq 0 ]; then
    mvn -DskipTests -Dspring.profiles.active=test clean package deploy
elif [ $1 == "all" ]; then
    mvn -DskipTests clean package deploy
else
    mvn $@
    exit
fi

if [ $? -ne 0 ]; then
    echo "error: mvn build failed!!!"
    exit 1
fi
