#!/bin/bash

SCRIPT_DIR=$(dirname $(realpath $0));
WORKSPACE_DIR="$(realpath ${SCRIPT_DIR}/../..)";
DOCKERFILE_DIR="$(realpath ${SCRIPT_DIR}/..)";
IMAGEM=$1;
PROJETO=$2;

## Cria o arquivo das variáveis de ambiente, caso não exista
mkdir -p ${HOME}/ambientes && touch -a "${HOME}/ambientes/${PROJETO}.env";

## Cria a pasta para o repositorio local do Maven, caso não exista (montada no container)
mkdir -p ${HOME}/.m2

## Cria a pasta para as dependências do npm no projeto, caso não exista (montada no container)
mkdir -p ${WORKSPACE_DIR}/frontend/node_modules

## Garante que a imagem seja criada, caso ainda não exista localmente no docker
if docker image inspect ${IMAGEM} >/dev/null 2>&1
then
  echo "Docker image ${IMAGEM} encontrada localmente. Build desnecessário."
else
  echo "Docker image ${IMAGEM} não encontrada localmente. Realizando build da imagem a partir do Dockerfile..."
  cd "${DOCKERFILE_DIR}"
  docker image build -t ${IMAGEM} .
fi
