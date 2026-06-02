pipeline {
    agent any

    options {
        timeout(time: 30, unit: 'MINUTES')
        timestamps()
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
                sh 'mvn clean test'
            }
            post {
                always {
                    echo 'Архивируем результаты Allure'
                    archiveArtifacts artifacts: 'target/allure-results/**', allowEmptyArchive: true
                }
            }
        }

        stage('Generate Allure Report') {
            when {
                expression {
                    script {
                        def file = new File("${env.WORKSPACE}/target/allure-results")
                        return file.exists() && file.isDirectory() && file.list().length > 0
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
            cleanWs()
        }
        success {
            echo 'Сборка прошла успешно на Java 21!'
        }
        failure {
            echo 'Сборка завершилась с ошибкой на Java 21!'
        }
    }
}
