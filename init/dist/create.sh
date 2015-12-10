#!/bin/bash

checkOs(){
    cygwin=false
    darwin=false
    case "`uname`" in
    Linux*) linux=true;;
    Darwin*) darwin=true;;
    esac
}

checkEnv(){
    if which java >/dev/null; then
        echo "Checking Java passed"
    else
        echo "Java not found in you machine, please instance it first."
        exit 1
    fi

    if which mvn >/dev/null; then
        echo "Checking Maven passed"
    else
        echo "Maven not found in you machine, please instance it first."
        exit 1
    fi

    checkOs
}

downloadInitProject() {
    curl -sOL ${init_project_url}
    if [ $? != 0 ];
        then
        echo "cannot download the prototype project"
        exit 1
    fi
}

cleanDownload() {
    echo "remove download"
    if [ -f ${WORK_DIR}/demo.tar.gz ];
    then
        rm -f ${WORK_DIR}/demo.tar.gz
    fi
}

prepareParams() {
    while [ ""${confirm} != "Y" ]
    do
        while [ "X"${project_name} == "X" ]
        do
            read -p "Please Enter the project name: " name

            if [ -f ${name} ];
            then 
                echo "There is a file with name ${name} exist"
                continue
            fi

            if [ -d ${name} ];
            then
                echo "The is already exist a directory with name ${name}"
                continue
            fi

            project_name=${name}
        done

        while [ "X"${package} == "X" ]
        do
            read -p "Please Enter the base package: " pkg
            echo "${pkg}"
            package=${pkg}
        done

        while [ "X"${module_name} == "X" ]
        do
            read -p "Please enter the module name: " module
            echo "${module}"
            module_name=${module}
        done

        echo "Project Name : ${project_name}"
        echo "Base Package : ${package}"
        echo "Module Name : ${module_name}"
        read -p "Is this correct? (Y/n) " input
        if [ "X"${input} == "XY" ] || [ "X"${input} == "Xy" ];
            then
            confirm="Y"
        else
            project_name=""
            package=""
            module_name=""
        fi
    done
    echo "Prepare params finished."
}

generateProject() {
    if [ -f ${init_project_pkg_name} ];
        then
        echo "invalid state for initial project."
        exit 1;
    fi
    tar xzf ${init_project_pkg_name}.tar.gz;
    mv ${init_project_pkg_name} ${project_name}

    echo "start initializing project structure"
    package_path=${package//./\/}
    mkdir -p ${project_name}/src/main/java/${package_path} 
    mkdir -p ${project_name}/src/test/java/${package_path} 

    if [ -d ${project_name}/src/main/java/com/baidu/oped/sia/business/ ];
    then
        mv ${project_name}/src/main/java/com/baidu/oped/sia/business/* ${project_name}/src/main/java/${package_path} 
        removeEmptyDirectory ${project_name}/src/main/java/com/baidu/oped/sia/business/
    fi
    
    if [ -d ${project_name}/src/main/test/com/baidu/oped/sia/business/ ];
        then
        mv ${project_name}/src/main/test/com/baidu/oped/sia/business/* ${project_name}/test/main/java/${package_path} 
        removeEmptyDirectory ${project_name}/src/main/test/com/baidu/oped/sia/business/
    fi

    echo "Rename package name"
    for file in `find ./${project_name} -type f | grep -v build/bin/babysitter`
    do
        echo "process file ${file}"
        if ${darwin}; then
            sed -i '' -e "s%com.baidu.oped.sia.business%${package}%g" ${file}
        fi
        if ${linux}; then
            sed -i -e "s%com.baidu.oped.sia.business%${package}%g" ${file}
        fi
    done

    echo "Rename project name"
    for file in `find ./${project_name} -type f | grep -v build/bin/babysitter`
    do
        echo "process file ${file}"
        if ${darwin}; then
            sed -i '' -e "s%demo%${project_name}%g" ${file}
        fi
        if ${linux}; then
            sed -i -e "s%demo%${project_name}%g" ${file}
        fi
    done

    if ${darwin}; then
        sed -i '' -e "s%${module_name}%com/baidu/demo%g" ./${project_name}/BCLOUD
    fi
    if ${linux}; then
        sed -i -e "s%${module_name}%com/baidu/demo%g" ./${project_name}/BCLOUD
    fi
}

removeEmptyDirectory() {
    isEmpty="True"
    base=$1
    if [ ! -d ${base} ];
        then
        return
    fi
    echo "${base}"
    cd ${base}
    while [ ""${isEmpty} == "True" ];
    do
        current_path=`pwd`
        entries=`ls ${current_path}`
        if [ -z ${entries} ];
            then
            cd ..
            rm -rf $current_path
        else
            isEmpty="False"
        fi
    done
    cd ${WORK_DIR}
}

buildProject() {
    echo "Start building project"
    cd ${project_name} && mvn clean package > build.log && cd -
    if [ $? != 0 ];
    then
        echo "Build prject failed."
        echo "Please checking the build.log file for details."
        exit 1
    fi
    echo "Build success"
    cd ${WORK_DIR}
}


WORK_DIR=`dirname $0`
WORK_DIR=`cd ${WORK_DIR}; pwd`
echo "Work dir: ${WORK_DIR}"

init_project_url=https://raw.githubusercontent.com/masonmei/sia-boot-custom/master/init/dist/demo.tar.gz
init_project_pkg_name=demo
project_name=
package=
module_name=

checkEnv
prepareParams
downloadInitProject
generateProject
cleanDownload
buildProject

