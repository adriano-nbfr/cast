ARG VARIANT="21-jdk-noble"
FROM eclipse-temurin:${VARIANT}

# Configura o shell para o bash
ENV SHELL=/bin/bash

# Parametros que podem ser passados ao container na criacao
ARG ARG_TIMEZONE="America/Sao_Paulo"
ARG ARG_LANG="pt_BR.UTF-8"
ARG ARG_LANGUAGE="pt_BR"

# Cria um usuario/grupo no container com os mesmos ids do usuario/grupo local no host, para nao usar o root
# Obs: Se a imagem base ja fizer isso, pode-se apenas aproveitar o usuario existente
ARG USERNAME=userdesenv
ARG USER_UID=1000
ARG USER_GID=$USER_UID

# Imagem já vem com usuário padrão, mas é preciso remover para usar o GID 1000
RUN userdel -r ubuntu
RUN groupadd --gid $USER_GID $USERNAME \
    && useradd --uid $USER_UID --gid $USER_GID -m $USERNAME

# Instala certificados para evitar erros ao baixar dependências/extensões pela rede do MPF
ARG DIR_CRT=/usr/local/share/ca-certificates
ARG DIR_PEM=/etc/ssl/certs
ARG PEM_REDE_MPF=${DIR_PEM}/CertificadosRedeMPF.pem
ARG PEM_REPO_MAVEN=${DIR_PEM}/RepositoriosMaven.pem
ARG JAVA_CACERTS=$JAVA_HOME/lib/security/cacerts
ENV NODE_EXTRA_CA_CERTS=${PEM_REDE_MPF}
COPY certificados/individuais/*.crt ${DIR_CRT}/
COPY certificados/*.pem ${DIR_PEM}/

RUN update-ca-certificates \
    && keytool -importcert -noprompt -keystore ${JAVA_CACERTS} -storepass changeit -file ${PEM_REDE_MPF} -alias "rede-mpf" \
    && keytool -importcert -noprompt -keystore ${JAVA_CACERTS} -storepass changeit -file ${PEM_REPO_MAVEN} -alias "repositorios-maven"

# Instala dependencias e configura idioma
RUN apt-get update \
    && apt-get -y --no-install-recommends install git locales \
    && locale-gen ${ARG_LANG}

# Instala o Node, NPM e Angular-Cli.
RUN curl -sL https://deb.nodesource.com/setup_22.x | bash - \
    && apt-get -y install nodejs \
    && npm set strict-ssl false && npm install -g @angular/cli@^20.3 && npm set strict-ssl true

# Instala o Chrome browser para permitir testes unitários do front end
RUN curl -fsSL https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list \
    && apt-get update \
    && apt-get -y install google-chrome-stable

# Variaveis de ambiente internas ao container
ENV LANG=${ARG_LANG} LC_ALL=${ARG_LANG} LANGUAGE=${ARG_LANGUAGE} TZ=${ARG_TIMEZONE}

# Indica as portas usadas pelo container
EXPOSE 8080
EXPOSE 4200

# Altera o usuario padrao do container
USER $USERNAME
