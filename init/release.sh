#!/bin/bash

WORK_DIR=`dirname $0`
WORK_DIR=`cd ${WORK_DIR}; pwd`
echo "Work dir: ${WORK_DIR}"

initDist(){
    if [ ! -d  ${WORK_DIR}/dist ];
    then
        echo "Create dist folder"
        mkdir ${WORK_DIR}/dist
    else
        echo "Clean dist folder"
        find ${WORK_DIR}/dist/ -name '*.tar.gz' |xargs rm -f
    fi
}

copyScripts() {
    echo "Copy scripts"
    for file in `ls ${WORK_DIR}/ |grep ".sh" |grep -v "release.sh"`
    do
        cp ${WORK_DIR}/${file} ${WORK_DIR}/dist/
    done
}

copyAndDistFolders(){
    echo "Copy and dist folders"
    for file in `ls ${WORK_DIR}/ |grep -v dist`
    do
        if [ -d ${file} ];
        then
            echo "Start to process ${WORK_DIR}/${file}"
            cp -rf ${WORK_DIR}/${file} ${WORK_DIR}/dist/${file}
            find ${WORK_DIR}/dist/${file} -name '.svn' | xargs rm -rf
            find ${WORK_DIR}/dist/${file} -name '.git' | xargs rm -rf

            cd ${WORK_DIR}/dist && tar zcf ${file}.tar.gz ${file}
            rm -rf ${WORK_DIR}/dist/${file}
        fi
    done
}

initDist
copyScripts
copyAndDistFolders

echo "Dist complete"