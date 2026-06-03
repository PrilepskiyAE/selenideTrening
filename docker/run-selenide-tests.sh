#!/bin/bash

# Запуск виртуального дисплея
Xvfb :99 -screen 0 1920x1080x24 &
export DISPLAY=:99

# Ожидание готовности Xvfb
sleep 3

# Запуск тестов (пример для Maven)
cd /home/jenkins/tests
mvn test -Dselenide.headless=true

# Остановка Xvfb
pkill Xvfb
