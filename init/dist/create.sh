#!/bin/bash

checkOs(){
    linux=false
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
    if [ -f ${init_package_name} ];
    then
        return 0
    fi
    curl -sOL ${init_package_download_url}
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

prepareProjectName() {
    local local_name=""
    while [ "X${local_name}" == "X" ]
    do
        read -p "请输入项目名称 [${project_name}]: " local_name

        if [ "X${project_name}" != "X" ] && [ -z ${local_name} ];
        then
            break
        fi

        if [ -z ${local_name} ];
        then
            echo -e "项目名称不能为空!\n"
            continue
        fi

        if [ -f ${local_name} ];
        then
            echo "该目录下已经存在名称为${local_name}的文件!"
            local_name=""
            continue
        fi

        if [ -d ${local_name} ];
        then
            echo "当前目录下已经存在名称为 ${local_name} 的目录!\n"
            local_name=""
            continue
        fi
        project_name=${local_name}
    done
    echo ""
}

prepareBasePackage() {
    local pkg=""
    while [ "X${pkg}" == "X" ]
    do
        read -p "请输入项目包名[${package}]: " pkg
        if [ "X${package}" != "X" ] && [ -z ${pkg} ];
        then
            break
        fi

        if [ -z ${pkg} ];
        then
            echo -e "项目包名不能为空!\n"
            continue
        fi
        package=${pkg}
    done
    echo ""
}

prepareModulePath() {
    local module=""
    while [ "X${module}" == "X" ]
    do
        read -p "请输入项目模块路径[${module_name}]: " module

        if [ "X${module_name}" != "X" ] && [ -z ${module} ];
        then
            break
        fi

        if [ -z ${module} ];
        then
            echo -e "项目模块路径不能为空!\n"
            continue
        fi
        module_name=${module}
    done
    echo ""
}

preparePort() {
    local port=""
    while [ "X${port}" == "X" ]
    do
        read -p "请输入服务端口[${application_port}]: " port

        if [ "X${application_port}" != "X" ] && [ -z ${port} ];
        then
            break
        fi

        if [ -z ${port} ];
        then
            echo -e "服务端口不能为空!\n"
            continue
        fi
        application_port=${port}
    done
    echo ""
}

prepareDeployPath() {
    local deploy=""
    while [ "X${deploy}" == "X" ]
    do
        read -p "请输入部署路径[${deploy_path}]: " deploy

        if [ "X${deploy_path}" != "X" ] && [ -z ${deploy} ];
        then
            break
        fi

        if [ -z ${deploy} ];
        then
            echo -e "部署路径不能为空!\n"
            continue
        fi
        deploy_path=${deploy}
    done
    echo ""
}

showParams() {
    echo "项目配置信息如下:"
    echo "========================================="
    echo "项目名称: ${project_name}"
    echo "包名: ${package}"
    echo "模块路径: ${module_name}"
    echo "部署路径: ${deploy_path}"
    echo "服务端口: ${application_port}"
    echo "========================================="
    echo ""
}

prepareParams() {
    local confirm=""
    while [ "${confirm}" != "Y" ]
    do
        prepareProjectName

        prepareBasePackage

        prepareModulePath

        prepareDeployPath

        preparePort

        showParams
        read -p "是否正确? (Y/n) " input
        if [ "X"${input} == "XY" ] || [ "X"${input} == "Xy" ];
            then
            confirm="Y"
        fi
    done
    echo "Prepare params finished."
    echo ""
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

    if [ -d ${project_name}/src/test/java/com/baidu/oped/sia/business/ ];
        then
        mv ${project_name}/src/test/java/com/baidu/oped/sia/business/* ${project_name}/src/test/java/${package_path}
        removeEmptyDirectory ${project_name}/src/test/java/com/baidu/oped/sia/business/
    fi


    if ${darwin}; then
        sed -i "" -e "s%com/baidu/demo%${module_name}%g" ./${project_name}/BCLOUD
    fi
    if ${linux}; then
        sed -i -e "s%com/baidu/demo%${module_name}%g" ./${project_name}/BCLOUD
    fi

    echo "Rename package name"
    for file in `find ./${project_name} -type f | grep -v ./${project_name}/build/`
    do
        echo "process file ${file}"
        if ${darwin}; then
            sed -i "" -e "s%com.baidu.oped.sia.business%${package}%g" ${file}
        fi
        if ${linux}; then
            echo "linux"
            sed -i -e "s%com.baidu.oped.sia.business%${package}%g" ${file}
        fi
    done

    echo "Rename project name"
    for file in `find ./${project_name} -type f | grep -v ./${project_name}/build/`
    do
        echo "process file ${file}"
        if ${darwin}; then
            sed -i "" -e "s%demo%${project_name}%g" ${file}
        fi
        if ${linux}; then
            sed -i -e "s%demo%${project_name}%g" ${file}
        fi
    done
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
        local current_path=`pwd`
        entries=`ls ${current_path}`
        if [ -z ${entries} ];
            then
            cd ..
            rm -rf ${current_path}
        else
            isEmpty="False"
        fi
    done
    cd ${WORK_DIR}
}

initialProject() {
    local PROJECT_DIR=${WORK_DIR}/${project_name}
    mv ${PROJECT_DIR}/build/bin/${init_project_pkg_name}.sh ${PROJECT_DIR}/build/bin/${project_name}.sh
    mv ${PROJECT_DIR}/build/bin/${init_project_pkg_name}_control ${PROJECT_DIR}/build/bin/${project_name}_control

    if ${darwin}; then
        sed -i '' -e "1,10s%^readonly PROJECT=${init_project_pkg_name}%readonly PROJECT=${project_name}%g" ${PROJECT_DIR}/build/bin/${project_name}.sh
        sed -i '' -e "1,10s%^readonly MAIN_PORT=${init_project_port}%readonly MAIN_PORT=${application_port}%g" ${PROJECT_DIR}/build/bin/${project_name}.sh

        sed -i '' -e "1,10s%^PROJECT_NAME=${init_project_pkg_name}%PROJECT_NAME=${project_name}%g" ${PROJECT_DIR}/build.sh

        sed -i '' -e "1s%${init_deploy_path}%${deploy_path}%g" ${PROJECT_DIR}/build/conf/babysitter.conf
        sed -i '' -e "2,\$s%${init_project_pkg_name}%${project_name}%g" ${PROJECT_DIR}/build/conf/babysitter.conf
    fi

    if ${linux}; then
        sed -i -e "1,10s%^readonly PROJECT=${init_project_pkg_name}%readonly PROJECT=${project_name}%g" ${PROJECT_DIR}/build/bin/${project_name}.sh
        sed -i -e "1,10s%^readonly MAIN_PORT=${init_project_port}%readonly MAIN_PORT=${application_port}%g" ${PROJECT_DIR}/build/bin/${project_name}.sh

        sed -i -e "1,10s%^PROJECT_NAME=${init_project_pkg_name}%PROJECT_NAME=${project_name}%g" ${PROJECT_DIR}/build.sh

        sed -i -e "1s%${init_deploy_path}%${deploy_path}%g" ${PROJECT_DIR}/build/conf/babysitter.conf
        sed -i -e "2,\$s%${init_project_pkg_name}%${project_name}%g" ${PROJECT_DIR}/build/conf/babysitter.conf
    fi

    echo "Application configuration applied."
}


buildProject() {
    echo "Start building project"
    cd ${project_name} && mvn clean package > build.log && cd -
    if [ $? != 0 ];
    then
        echo "Build project failed."
        echo "Please checking the build.log file for details."
        exit 1
    fi
    echo "Build success"
    rm -f build.log
    cd ${WORK_DIR}
}




WORK_DIR=`dirname $0`
WORK_DIR=`cd ${WORK_DIR}; pwd`
echo "当前目录: ${WORK_DIR}"

init_package_name=demo.tar.gz
init_package_download_url=https://raw.githubusercontent.com/masonmei/sia-boot-custom/master/init/dist/${init_package_name}
init_project_pkg_name=demo
init_project_port=8888
init_deploy_path=/home/work/bcm/demo
project_name=
package=
module_name=
application_port=
deploy_path=

#
checkEnv
prepareParams
downloadInitProject
generateProject
initialProject
cleanDownload
buildProject

