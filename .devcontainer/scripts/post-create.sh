#!/bin/bash

## Instala as dependências do frontend
bash ${BASH_SOURCE%/*}/npm-install.sh

sleep 3

## Realiza o package da aplicação
bash ${BASH_SOURCE%/*}/maven-package.sh
