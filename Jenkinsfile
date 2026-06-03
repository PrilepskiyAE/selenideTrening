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
        echo 'Запуск тестов на Java 21...'
        sh 'mvn -Dmaven.repo.local=${WORKSPACE}/.m2/repository clean test'
    }
    post {
        always {
            echo 'Архивируем результаты Allure'
            archiveArtifacts(
                artifacts: 'target/allure-results/**',
                allowEmptyArchive: true,
                fingerprint: true
            )
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
