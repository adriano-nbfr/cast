#!/bin/bash

SCRIPT_DIR=$(dirname $(realpath $0));
WORKSPACE_DIR="$(realpath ${SCRIPT_DIR}/../..)";
PROFILE=$1;

## Realiza o package da aplicação
if [ -e "${WORKSPACE_DIR}/mvnw" ]
then
  echo "Realizando package do projeto Maven..."
  [ -z ${PROFILE} ] && echo "Profile default" || echo "Profile ${PROFILE}"

  cd "${WORKSPACE_DIR}"
  [ -z ${PROFILE} ] && ./mvnw package || ./mvnw package -P ${PROFILE}
fi
