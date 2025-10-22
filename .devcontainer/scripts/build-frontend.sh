#!/bin/bash

SCRIPT_DIR=$(dirname $(realpath $0));
WORKSPACE_DIR="$(realpath ${SCRIPT_DIR}/../..)";
TARGET_DIR="${WORKSPACE_DIR}/target/classes/static";
FRONTEND_DIR="${WORKSPACE_DIR}/frontend";
BUILD_DIR="${FRONTEND_DIR}/dist/browser";
CONFIGURATION=$1;

## Realiza o build do frontend
if [ -e "${FRONTEND_DIR}/package.json" ]
then
  cd ${FRONTEND_DIR}

  echo "Instalando dependências do frontend..."
  npm install

  echo "Realizando o build do frontend..."
  [ -z ${CONFIGURATION} ] && ng build || ng build --configuration ${CONFIGURATION}

  echo "Copiando os arquivos do build para a pasta de destino..."
  mkdir -p ${TARGET_DIR}/frontend # cria a pasta se não existir
  rm -rf ${TARGET_DIR}/frontend/* # limpa todos os arquivos antigos
  cp -r ${BUILD_DIR}/* ${TARGET_DIR}/frontend/ # copia os novos arquivos do build
  mv ${TARGET_DIR}/frontend/index.html ${TARGET_DIR}/ # move a index.html para a raiz
  cp ${TARGET_DIR}/frontend/favicon.ico ${TARGET_DIR}/ # Deixa uma cópia do favicon também na raiz, como padrão
fi
