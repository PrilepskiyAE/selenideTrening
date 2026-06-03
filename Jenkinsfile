pipeline {
    agent any

    options {
        timeout(time: 30, unit: 'MINUTES')
        timestamps()
    }

    tools {
        maven 'Maven 3.9.9'  // Имя из Global Tool Configuration
        jdk 'JDK 21'       // Имя из Global Tool Configuration
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

      stage('Build and Test') {
    steps {
        script {
            // Запускаем Selenium контейнер
            sh 'docker run -d --name selenium-chrome --shm-size=2g -p 4444:4444 selenium/standalone-chrome:latest'
            
            // Ждём готовности Selenium
            sh '''
                until curl -f http://localhost:4444/wd/hub/status; do
                    sleep 5
                done
            '''
            
            echo 'Запуск тестов на Java 21 с подключением к Selenium Grid...'
            sh 'mvn -Dselenium.url=http://localhost:4444/wd/hub -Dmaven.repo.local=${WORKSPACE}/.m2/repository clean test'
        }
    }
    post {
        always {
            // Останавливаем контейнер после тестов
            sh 'docker stop selenium-chrome || true'
            sh 'docker rm selenium-chrome || true'
        }
    }
}

        stage('Generate Allure Report') {
            when {
                expression {
                    script {
                        def resultsDir = "${env.WORKSPACE}/target/allure-results"
                        return fileExists(resultsDir) &&
                               sh(script: "test -d '${resultsDir}' && ls -A '${resultsDir}' | grep -q .",
                                  returnStatus: true) == 0
                    }
                }
            }
            steps {
                echo 'Генерируем отчёт Allure...'
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }

    post {
        always {
            echo 'Очистка workspace...'
            cleanWs(
                notFailBuild: true,
                patterns: [[
                    type: 'EXCLUDE',
                    pattern: '**/target/allure-results/**'
                ]]
            )
        }
        success {
            echo 'Сборка прошла успешно на Java 21!'
        }
        failure {
            echo 'Сборка завершилась с ошибкой на Java 21!'
        }
    }
}
