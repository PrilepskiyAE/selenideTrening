pipeline {
   agent {
    docker {
        image 'maven:3.8.6-openjdk-21'
        args '-v $HOME/.m2:/root/.m2'
    }
}
    stages {
        stage('Hello') {
            steps {
                echo 'Hello, Jenkins is working!'
                sh 'pwd'
                sh 'ls -la'
            }
        }
    }
}
