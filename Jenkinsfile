pipeline {
    agent {
        docker {
            image 'maven:3.8.6-openjdk-11'
            args '-v $HOME/.m2:/root/.m2'  // кэшируем Maven-зависимости
        }
    }

    tools {
        maven 'Maven 3.8.6'
        jdk 'JDK 21'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean test'
            }
            post {
                always {
                    // Сохраняем результаты Allure для следующего этапа
                    archiveArtifacts artifacts: 'target/allure-results/**', allowEmptyArchive: true
                }
            }
        }

        stage('Generate Allure Report') {
            steps {
                // Генерируем отчёт Allure
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }

        stage('Publish JUnit Report') {
            when {
                expression { fileExists 'target/surefire-reports/*.xml' }
            }
            steps {
                junit 'target/surefire-reports/*.xml'
            }
        }
    }

    post {
        always {
            // Очищаем workspace после сборки
            cleanWs()
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
