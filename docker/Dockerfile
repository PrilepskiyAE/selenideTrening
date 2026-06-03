FROM jenkins/jenkins:2.555.2-jdk21

USER root

# Обновление системы и установка базовых утилит
RUN apt-get update && \
    apt-get install -y \
        lsb-release \
        ca-certificates \
        curl \
        wget \
        gnupg \
        unzip \
        vim \
    && rm -rf /var/lib/apt/lists/*

# Установка Docker CLI
RUN install -m 0755 -d /etc/apt/keyrings && \
    curl -fsSL https://download.docker.com/linux/debian/gpg -o /etc/apt/keyrings/docker.asc && \
    chmod a+r /etc/apt/keyrings/docker.asc && \
    echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] \
    https://download.docker.com/linux/debian $(. /etc/os-release && echo \"$VERSION_CODENAME\") stable" \
    | tee /etc/apt/sources.list.d/docker.list > /dev/null && \
    apt-get update && \
    apt-get install -y docker-ce-cli && \
    rm -rf /var/lib/apt/lists/*

# Проверка установки Docker CLI
RUN docker --version

# Установка Google Chrome
RUN curl -LO https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && \
    dpkg -i google-chrome-stable_current_amd64.deb || \
    (apt-get update && apt-get -f install -y && dpkg -i google-chrome-stable_current_amd64.deb) && \
    rm google-chrome-stable_current_amd64.deb

# Проверка установки Chrome
RUN google-chrome --version

USER jenkins

# Установка Jenkins‑плагинов
RUN jenkins-plugin-cli --plugins "blueocean docker-workflow json-path-api allure-jenkins-plugin workflow-aggregator"
