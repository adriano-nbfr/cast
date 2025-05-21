#!/bin/bash

SCRIPT_DIR=$(dirname $(realpath $0));
WORKSPACE_DIR="$(realpath ${SCRIPT_DIR}/../..)";
FRONTEND_DIR="${WORKSPACE_DIR}/frontend"

## Instala as dependências do frontend
if [ -e "${FRONTEND_DIR}/package.json" ]
then
  echo "Instalando as dependências do frontend..."
  cd "${FRONTEND_DIR}"
  npm install --loglevel http
fi
