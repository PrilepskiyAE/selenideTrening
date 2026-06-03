pipeline {
    agent {
        docker {
            image 'eclipse-temurin:21-jdk-jammy'
            args '-v /var/run/docker.sock:/var/run/docker.sock -v /tmp:/tmp'
        }
    }

    environment {
        SELENOID_URL = 'http://selenoid:4444/wd/hub'
        TEST_ENV = 'staging'
        MAVEN_OPTS = '-Dmaven.repo.local=.m2/repository'
        ALLURE_RESULTS = 'target/allure-results'
        ALLURE_REPORT = 'target/allure-report'
    }

    tools {
        maven 'Maven 3.9.9'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Dependencies') {
            steps {
                sh 'mvn clean compile -DskipTests=true'
            }
        }

        stage('Run Selenide Tests with Selenoid') {
            steps {
                script {
                    try {
                        sh """
                        mvn test \
                            -Dselenoid.url=$SELENOID_URL \
                            -Dtest.env=$TEST_ENV \
                            -Dbrowser=chrome \
                            -DbrowserVersion=latest
                        """
                    } catch (Exception e) {
                        echo "Tests failed: ${e.message}"
                        throw e
                    }
                }
            }
        }

        stage('Generate Allure Report') {
            when {
                expression { fileExists('target/allure-results') }
            }
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

        stage('Archive Test Results') {
            steps {
                archiveArtifacts artifacts: 'target/surefire-reports/**/*', \
                    onlyIfSuccessful: false, \
                    fingerprint: true
                archiveArtifacts artifacts: 'target/allure-results/**/*', \
                    onlyIfSuccessful: false, \
                    fingerprint: true
            }
        }
    }

    post {
        success {
            echo 'All tests passed successfully!'
            slackSend channel: '#qa-tests', message: '✅ Selenide tests PASSED on Selenoid!', color: '#00FF00'
        }
        failure {
            echo 'Tests failed!'
            slackSend channel: '#qa-tests', message: '❌ Selenide tests FAILED on Selenoid! Check the build.', color: '#FF0000'
        }
        always {
            cleanWs()
        }
    }
}
