pipeline {
    agent any

    options {
        timeout(time: 30, unit: 'MINUTES')  // Таймаут сборки — не зависнет навсегда
        timestamps()  // В логах будут метки времени
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                echo 'Запуск тестов...'
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
                    // Проверяем, что папка с результатами существует и не пуста
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
            echo 'Сборка прошла успешно!'
        }
        failure {
            echo 'Сборка завершилась с ошибкой!'
        }
    }
}
