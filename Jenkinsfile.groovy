pipeline {
    agent {
        docker {
            image 'maven:3.8.6-openjdk-21'
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
                    archiveArtifacts artifacts: 'target/allure-results/**', allowEmptyArchive: true
                }
            }
        }

        stage('Generate Allure Report') {
            steps {
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
            cleanWs()
        }
    }
}

