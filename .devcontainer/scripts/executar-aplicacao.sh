#!/bin/bash

SCRIPT_DIR=$(dirname $(realpath $0));
WORKSPACE_DIR="$(realpath ${SCRIPT_DIR}/../..)";
TARGET_JAR=$(ls ${WORKSPACE_DIR}/target/*.jar | head -n1)

## Realiza o package da aplicação
if [ -e ${TARGET_JAR} ]
then
  echo "Executando o pacote JAR da aplicação gerado pelo build..."
  java -jar ${TARGET_JAR}
else
  echo "Não foi encontrado um arquivo JAR para ser executado."
fi
